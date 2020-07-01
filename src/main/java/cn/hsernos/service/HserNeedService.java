package cn.hsernos.service;

import cn.hsernos.common.beans.User;
import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.dao.NeeduserMapper;
import cn.hsernos.pojo.Needuser;
import cn.hsernos.pojo.NeeduserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;


@Service
public class HserNeedService {

    private static final Logger logger = LoggerFactory.getLogger(HserNeedService.class);

    @Autowired
    private NeeduserMapper needuserMapper;

    /**
     * 用户登录
     *
     * @param session  HttpSession
     * @param username 用户昵称
     * @param password 密码
     */
    public void login(HttpSession session, String username, String password) {
        CheckUtil.notEmpty(username, "用户名为空");
        CheckUtil.notEmpty(password, "密码为空");
        logger.info("[用户<{}>登录中]", username);
        Needuser needuser = findUserByPassword(username, password);
        if (needuser == null) {
            logger.info("[登录失败，用户名或密码错误");
            CheckUtil.fail("用户名或密码错误");
        } else {
            User user = new User();
            user.setId(needuser.getNid());
            user.setUsername(needuser.getUsername());
            user.setPower((int) needuser.getPower());
            session.setAttribute("user", user);
            logger.info("[登录成功]");
        }
    }


    /**
     * 用户注册
     *
     * @param needuser 需求方
     * @param vcode    验证码
     * @param session  HttpSession
     */
    public void register(Needuser needuser, String vcode, HttpSession session) {
        //nid和audit两个属性必须为空，其他属性必须不为空
        CheckUtil.mustNullOtherOpposite(needuser, "注册数据异常", "power&nid&audit");
        CheckUtil.notEmpty("vcode", "注册码为空");
        logger.info("[注册开始，参数[{}]]", needuser);
        String sVcode = (String) session.getAttribute("vcode");
        if (sVcode == null) {
            CheckUtil.fail("还未发送验证码");
        }
        if (!sVcode.equals(vcode)) {
            CheckUtil.fail("验证码错误");
        }
        String email = (String) session.getAttribute("email");
        if (!email.equals(needuser.getEmail())) {
            logger.info("[注册失败，前后邮箱不一致]");
            CheckUtil.fail("前后邮箱不一致");
        }
        if (findUser(needuser.getUsername(), null) != null) {
            logger.info("[注册失败，该用户名已被注册]");
            CheckUtil.fail("该用户名已被注册");
        }
        if (findUser(null, needuser.getEmail()) != null) {
            logger.info("[注册失败，该邮箱已被注册]");
            session.removeAttribute("email");
            session.removeAttribute("vcode");
            CheckUtil.fail("该邮箱已被注册");
        }
        needuser.setPower((byte) -1);
        needuserMapper.insertSelective(needuser);
        session.removeAttribute("email");
        session.removeAttribute("vcode");
        logger.info("[注册成功]");
    }


    public void findPassword(String email, String password, String vcode, HttpSession session) {
        CheckUtil.notEmpty(email, "邮箱不能为空");
        CheckUtil.notEmpty(vcode, "验证码不能为空");
        logger.info("[找回密码中,参数[email:{}]]", email);
        String sVcode = (String) session.getAttribute("vcode");
        if (sVcode == null) {
            CheckUtil.fail("还未发送验证码");
        }
        if (!sVcode.equals(vcode)) {
            CheckUtil.fail("验证码错误");
        }
        Needuser needuser = findUser(null, email);
        if (needuser == null) {
            session.removeAttribute("email");
            session.removeAttribute("vcode");
            CheckUtil.fail("该邮箱未注册");
        }
        needuser.setPassword(password);
        needuserMapper.updateByPrimaryKeySelective(needuser);
        session.removeAttribute("email");
        session.removeAttribute("vcode");
        logger.info("[找回密码成功]");
    }

    public void setUser(Integer uid, Needuser needuser) {
        CheckUtil.mustNullOtherOpposite(needuser, "修改异常", "nid&audit&password&power");
        CheckUtil.notNull(uid, "修改异常");
        logger.info("[账户信息修改中，参数[{}]]", needuser);

        needuser.setNid(uid);
        if (needuser.getUsername() != null) {
            Needuser needuser1 = findUser(needuser.getUsername(), null);
            if (needuser1 != null && !needuser.getNid().equals(uid)) {
                logger.info("[修改失败，该用户名已被注册]");
                CheckUtil.fail("该用户名已被注册");
            }
        }
        if (needuser.getEmail() != null) {
            Needuser needuser1 = findUser(null, needuser.getEmail());
            if (needuser1 != null && !needuser.getNid().equals(uid)) {
                logger.info("[修改失败，该邮箱已被注册]");
                CheckUtil.fail("该邮箱已被注册");
            }
        }
        needuserMapper.updateByPrimaryKeySelective(needuser);
        logger.info("[修改成功]");
    }


    public void setPassword(String oldPassword, String newPassword, Integer uid) {
        CheckUtil.notNull(oldPassword, "参数不能为空");
        CheckUtil.notNull(newPassword, "参数不能为空");
        logger.info("[修改密码中]");
        if (!oldPassword.trim().equals(newPassword.trim())) {
            boolean is = needuserMapper.selectByPrimaryKey(uid).getPassword().trim().equals(oldPassword.trim());
            if (is) {
                Needuser needuser = new Needuser();
                needuser.setNid(uid);
                needuser.setPassword(newPassword.trim());
                needuserMapper.updateByPrimaryKeySelective(needuser);
                logger.info("[修改成功]");
            } else {
                logger.info("[修改失败]");
                CheckUtil.fail("原密码错误");
            }
        }
    }


    /**
     * @param uid 用户id
     * @return 自己的账号信息
     */
    public Needuser getUser(Integer uid) {
        CheckUtil.notNull(uid, "参数为空");
        logger.info("[查询自己的信息中]");
        Needuser needuser = needuserMapper.selectByPrimaryKey(uid);
        needuser.setPassword(null);
        needuser.setPower(null);
        logger.info("[查询成功]");
        return needuser;
    }

    /**
     * 根据用户名或电子邮箱查找用户
     *
     * @param username 用户名
     * @param email    电子邮箱
     * @return 用户信息
     */
    public Needuser findUser(String username, String email) {
        logger.info("[根据唯一键查找用户，参数[username:{},email:{}]]", username, email);
        NeeduserExample example = new NeeduserExample();
        if (username != null) {
            example.or().andUsernameEqualTo(username);
        } else {
            example.or().andEmailEqualTo(email);
        }
        List<Needuser> list = needuserMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    public Needuser findUserByPassword(String username, String password) {
        logger.info("[根据唯一键查找用户，参数[username:{},password:{}]]", username, password);
        NeeduserExample example = new NeeduserExample();
        example.or().andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        List<Needuser> list = needuserMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
