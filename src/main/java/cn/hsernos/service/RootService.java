package cn.hsernos.service;


import cn.hsernos.common.beans.User;
import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.dao.*;
import cn.hsernos.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;



@Service
public class RootService {
    private static final Logger logger = LoggerFactory.getLogger(RootService.class);
    @Autowired
    public AdminMapper adminMapper;

    @Autowired
    public ResultMapper resultMapper;

    @Autowired
    public CollegeMapper collegeMapper;


    @Autowired
    private VoMapper voMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private PatentMapper patentMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private TerraceMapper terraceMapper;

    @Autowired
    private SkilluserMapper skilluserMapper;

    @Autowired
    private DemandMapper demandMapper;

    //查询专利信息
    public PageInfo<Patent> getAll(Integer cid, Integer pageno) {
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询专利信息]");
        PageHelper.startPage(pageno, 6);
        List<Patent> p = adminMapper.getAllPatent(cid);
        return new PageInfo<Patent>(p);
    }


    public PageInfo<Terrace> getAllTerrace(Integer cid, Integer pageno) {

        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询平台资源信息]");
        PageHelper.startPage(pageno, 6);
        List<Terrace> terraces = adminMapper.getAllTerrace(cid);
        return new PageInfo<Terrace>(terraces);
    }

    /**
     * 管理员查询团队信息
     *
     * @param session
     * @param cid
     * @param pageno
     * @return 团队信息
     */
    public PageInfo<Team> getAllTeam(HttpSession session, Integer cid, Integer pageno) {
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询团队资源信息]");
        User user = (User) session.getAttribute("user");
        logger.info("[管理员查询团队资源信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        PageHelper.startPage(pageno, 6);
        List<Team> teams = adminMapper.getAllTeam(cid);
        logger.info("[管理员查询团队资源信息成功]");
        return new PageInfo<Team>(teams);
    }


    /**
     * 查询软件著作权信息
     *
     * @param cid
     * @param pageno
     * @param uid
     * @return 软件著作权信息
     */
    public PageInfo<Software> getAllSoftware(Integer cid, Integer pageno, Integer uid) {
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询软件著作权信息]");
        logger.info("[管理员查询软件著作权信息，参数[id:{}]]", uid);
        PageHelper.startPage(pageno, 6);
        List<Software> softwares = adminMapper.getAllSoftware(cid);
        logger.info("[管理员查询软件著信息成功]");
        return new PageInfo<Software>(softwares);
    }


    /**
     * 获取所有对接成果信息
     *
     * @param pageno
     * @return
     */
    public PageInfo<Result> getAllResult(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        Admin admin = adminMapper.selectByPrimaryKey(id);
        logger.info("[管理员查询对接成果信息]");
        PageHelper.startPage(pageno, 6);
        List<Result> resultList = resultMapper.selectByCid(admin.getCid());
        logger.info("[管理员查技术需求方信息成功]");
        return new PageInfo<Result>(resultList);
    }


    //按成果分类查询成果信息
    public PageInfo getResultByAid(Integer aid, Integer pageno) {
        logger.info("[按成果类别信息查询]");
        PageHelper.startPage(pageno, 6);
        List<Result> resultList = resultMapper.selectByAid(aid);
        return new PageInfo<Result>(resultList);
    }

    public PageInfo<Result> getAllResultByCidAndAid(Integer cid, Integer aid, Integer pageno) {
        PageHelper.startPage(pageno, 6);
        logger.info("[查询对接成果信息]");
        List<Result> results = resultMapper.selectAllResultByCidAndAid(cid, aid);
        logger.info("[管理员查技术需求方信息成功]");
        return new PageInfo<Result>(results);

    }

    /**
     * 获取学院信息
     *
     * @return
     */
    public List<College> getAllCollege() {
        return collegeMapper.getColleges();
    }


    //管理员模块
    public PageInfo<Admin> getAllAdmin(Integer pageno) {
        logger.info("[查询所有管理员]");
        PageHelper.startPage(pageno, 6);
        List<Admin> adminList = adminMapper.getAllAdmin();
        logger.info("[查询管理员信息成功]");
        return new PageInfo<Admin>(adminList);
    }

    /**
     * 重置密码  将密码重置为123456
     *
     * @param uid
     */
    public void resettingPassword(Integer uid) {
        logger.info("[管理员重置密码]");
        int row = adminMapper.resettingPassword(uid, "123456");
        if (row == 0) {
            logger.info("[系统异常，密码重置失败]");
            CheckUtil.fail("系统异常，密码重置失败");
        } else {
            logger.info("[密码重置成功]");
        }
    }


    /**
     * 删除管理员
     *
     * @param uid
     */
    public void deleteAdmin(Integer uid) {
        logger.info("[超级管理员删除管理员账户]");
        int row = adminMapper.deleteAdmin(uid);
        if (row == 0) {
            logger.info("[系统异常，删除账户失败]");
            CheckUtil.fail("系统异常，删除账户失败");
        } else {
            logger.info("[超级管理员删除管理员账户成功]");
        }
    }

    public void addAdmin(String username, Integer cid) {
        logger.info("[添加管理员]");
        Admin admin = adminMapper.getAdminByUser(username);

        if (admin != null) {
            logger.info("[账户添加失败，账户名已存在]");
            CheckUtil.fail("该账户已存在，请使用新账户名");
        }
        Byte power = 2;
        int row = adminMapper.addAdmin(username, "123456", power, cid);
        if (row == 0) {
            logger.info("[系统异常，添加账户失败]");
            CheckUtil.fail("系统异常，添加账户失败");
        } else {
            logger.info("[超级管理员添加管理员账户成功]");
        }
    }

    /**
     * 查询统计数据
     *
     * @param cid  部门id，查询条件，可以为空
     * @param some 1：年:2：季:3：月
     * @return 统计数据
     */
    public ArrayList<HserStatisticsVO> getBySome(Integer cid, Integer some) {
        CheckUtil.notNull(some, "参数为空");
        logger.info("[获取统计数据，参数[cid:{},Some:{}]]", cid, some);
        switch (some) {
            case 1:
                return voMapper.getByYear(cid);
            case 2:
                return voMapper.getByQuarter(cid);
            case 3:
                return voMapper.getByMonth(cid);
            default:
                return voMapper.getByYear(cid);
        }
    }


    public Object getResultSource(Integer rid) {
        logger.info("查询成果对应的资源信息");
        Result result = resultMapper.selectByPrimaryKey(rid);
        if (result.getSid() == null) {
            logger.info("[不存在该成果信息，参数{}]", rid);
            CheckUtil.fail("不存在该成果信息");
        }
        Integer sid = result.getSid();
        cn.hsernos.pojo.Service service = serviceMapper.selectByPrimaryKey(sid);
        Integer ids = service.getIds();
        Integer rid1 = service.getRid();
        Object resource = null;
        Byte audit = 0;
        switch (rid1) {
            case 1:
                resource = patentMapper.selectByPrimaryKey(ids);
                audit = 1;
                ((Patent) resource).setAudit(audit);//利用审核值来区别资源类别
                break;
            case 2:
                resource = schoolMapper.selectByPrimaryKey(ids);
                audit = 2;
                ((School) resource).setAudit(audit);
                break;
            case 3:
                resource = softwareMapper.selectByPrimaryKey(ids);
                audit = 3;
                ((Software) resource).setAudit(audit);
                break;
            case 4:
                resource = teamMapper.selectByPrimaryKey(ids);
                audit = 4;
                ((Team) resource).setAudit(audit);
                break;
            case 5:
                resource = terraceMapper.selectByPrimaryKey(ids);
                audit = 5;
                ((Terrace) resource).setAudit(audit);
                break;
        }
        logger.info("[查询成功]");
        return resource;
    }


    public HserSkillUserVO getResultSkilluser(Integer rid) {
        Result result = resultMapper.selectByPrimaryKey(rid);
        if (result.getSid() == null) {
            logger.info("[不存在该成果信息，参数{}]", rid);
            CheckUtil.fail("不存在该成果信息");
        }
        Integer uid = result.getUid();
        Skilluser skilluser = skilluserMapper.selectByPrimaryKey(uid);
        Skilluser skilluser1 = new Skilluser();
        skilluser1.setUsername(skilluser.getUsername());
        List<HserSkillUserVO> list = skilluserMapper.getSkilluserByusername(skilluser1);
        return list.get(0);
    }


    public Demand getResultDemand(Integer rid) {
        Result result = resultMapper.selectByPrimaryKey(rid);
        if (result.getSid() == null) {
            logger.info("[不存在该成果信息，参数{}]", rid);
            CheckUtil.fail("不存在该成果信息");
        }
        Integer did = result.getDid();
        Demand demand = demandMapper.selectByPrimaryKey(did);
        return demand;
    }


    public Result getResult(Integer rid) {
        return resultMapper.selectByPrimaryKey(rid);
    }
}
