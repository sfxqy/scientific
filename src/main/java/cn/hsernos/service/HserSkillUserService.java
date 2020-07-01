package cn.hsernos.service;

import cn.hsernos.common.beans.User;
import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.common.utils.FileUtil;
import cn.hsernos.dao.SkilluserMapper;
import cn.hsernos.dao.SkillusersMapper;
import cn.hsernos.pojo.Skilluser;
import cn.hsernos.pojo.SkilluserExample;
import cn.hsernos.pojo.Skillusers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Service
public class HserSkillUserService {
    private static final Logger logger = LoggerFactory.getLogger(HserSkillUserService.class);

    @Autowired
    private SkilluserMapper skilluserMapper;

    @Autowired
    private SkillusersMapper skillusersMapper;

    /**
     * 技术方登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户基本信息表
     */
    public void selectByPassword(String username, String password, HttpSession session) {
        CheckUtil.notNull(username, "用户名不能为空");
        CheckUtil.notNull(password, "密码不能为空");
        logger.info("[登录中，参数为：{用户名：{}，密码：xxxxxx}]", username);
        SkilluserExample example = new SkilluserExample();
        example.or().andUsernameEqualTo(username)
                .andPasswordEqualTo(password);
        List<Skilluser> list = skilluserMapper.selectByExample(example);
        Skilluser skilluser = null;
        if (list.size() > 0) {
            skilluser = list.get(0);
        }
        if (skilluser == null) {
            logger.info("[登录失败，账号或密码错误]");
            CheckUtil.fail("账号或密码错误");
        } else if (skilluser.getPower() == 0 || skilluser.getPower() == 3) {
            User user = new User();
            user.setPower((int) skilluser.getPower());
            user.setUsername(skilluser.getUsername());
            user.setId(skilluser.getUid());
            session.setAttribute("user", user);
            logger.info("[用户：<{}>登录成功]", user.getUsername());
        } else {
            logger.info("未知错误，请重新登录");
            CheckUtil.fail("未知错误，请重新登录");
        }
    }

    /**
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param uid         用户id
     */
    public void setPassword(String oldPassword, String newPassword, Integer uid) {
        CheckUtil.notNull(oldPassword, "参数不能为空");
        CheckUtil.notNull(newPassword, "参数不能为空");
        CheckUtil.notNull(uid, "参数不能为空");
        logger.info("[修改密码中]");
        if (!oldPassword.trim().equals(newPassword.trim())) {
            boolean is = skilluserMapper.selectByPrimaryKey(uid).getPassword().trim().equals(oldPassword.trim());
            if (is) {
                Skilluser skilluser = new Skilluser();
                skilluser.setUid(uid);
                skilluser.setPassword(newPassword.trim());
                skilluserMapper.updateByPrimaryKeySelective(skilluser);
                logger.info("[修改成功]");
            } else {
                logger.info("[修改失败]");
                CheckUtil.fail("原密码错误");
            }
        }
    }

    /**
     * @param skilluser  用户基础资料
     * @param skillusers 用户详细资料
     * @throws IOException
     */
    public void setUser(Skilluser skilluser, Skillusers skillusers, HttpSession session, Integer uid) throws IOException {
        //username和power两个属性必须为空
        System.out.println(skillusers.getEmail());
        CheckUtil.mustNull(skilluser, "参数异常", "username&power&password");
        //audit属性必须为空
        CheckUtil.mustNull(skillusers, "参数异常", "audit");
        if (skillusers.getFid() == 1) {
            CheckUtil.fail("专业领域选择非法");
        }
        skilluser.setUsername(null);
        skilluser.setPower(null);
        skilluser.setPassword(null);
        skillusers.setAudit(null);
        /*     skillusers.setEmail(null);*/
        logger.info("[修改用户基本信息中，参数[{},{}]]", skilluser, skillusers);
        skilluser.setUid(uid);
        skillusers.setUid(uid);
        String delImg = null;
        if (skillusers.getImg() != null) {
            String img = skillusersMapper.selectByPrimaryKey(uid).getImg();
            if (!img.equals(skillusers.getImg())) {
                delImg = img;
            }
        }
        skilluserMapper.updateByPrimaryKeySelective(skilluser);
        skillusersMapper.updateByPrimaryKeySelective(skillusers);
        FileUtil.deleteFile(session, delImg);
        logger.info("[修改成功]");
    }
}
