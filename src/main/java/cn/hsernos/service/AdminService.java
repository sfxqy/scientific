package cn.hsernos.service;

import cn.hsernos.common.beans.ResultBean;
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
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PatentMapper patentMapper;

    @Autowired
    private ServiceMapper serviceMapper;

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private TerraceMapper terraceMapper;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private NeeduserMapper needuserMapper;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private DemandlistMapper demandlistMapper;

    @Autowired
    private SkilluserMapper skilluserMapper;

    @Autowired
    private SkillusersMapper skillusersMapper;
    @Autowired
    private VoMapper voMapper;


    public Admin login(String username, String password, HttpSession session) {
        CheckUtil.notNull(username, "账号不能为空");
        CheckUtil.notNull(password, "密码不能为空");
        logger.info("[后台管理员登录中，参数为：{用户名：{}，密码：xxxxxx}]", username);
        Admin admin = adminMapper.login(username, password);
        if (admin == null) {
            logger.info("[后台管理员登录失败，账号或密码错误]");
            CheckUtil.fail("账号或密码错误");
        } else if (admin.getPower() == 1 || admin.getPower() == 2) {
            User user = new User();
            user.setPower((int) admin.getPower());
            user.setUsername(admin.getUsername());
            user.setId(admin.getUid());
            session.setAttribute("user", user);
            User u = (User) session.getAttribute("user");
            System.out.println("session域中是否有值：" + u == null);
            logger.info("[后台管理员用户：<{}>登录成功]", admin.getUsername());
        } else {
            logger.info("权限不够");
            CheckUtil.fail("权限不够");
        }
        return admin;
    }


    //获取用户的院别信息
    public Integer getUid(User user) {
        Integer uid = user.getId();
        Admin admin = adminMapper.selectByPrimaryKey(uid);
        return admin.getCid();
    }

    //获取专利资源信息
    public PageInfo<Patent> getAll(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        logger.info("[管理员查询专利资源信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        PageHelper.startPage(pageno, 6);
        List<Patent> p = adminMapper.getAllPatent(admin.getCid());
        logger.info("[管理员查询专利资源信息成功,数据量{}]", p.size());
        return new PageInfo<Patent>(p);
    }


    /**
     * 获取平台资源信息
     *
     * @param session
     * @param pageno
     * @return 平台信息
     */
    public PageInfo<Terrace> getAllTerrace(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        logger.info("[管理员查询平台资源信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        PageHelper.startPage(pageno, 6);
        List<Terrace> terrace = adminMapper.getAllTerrace(admin.getCid());
        logger.info("[管理员查询平台资源信息成功,数据量{}]", terrace.size());
        return new PageInfo<Terrace>(terrace);
    }


    /**
     * 获取团队信息
     *
     * @param session
     * @param pageno
     * @return 团队信息
     */
    public PageInfo<Team> getAllTeam(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        logger.info("[管理员查询团队资源信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        PageHelper.startPage(pageno, 6);
        List<Team> teams = adminMapper.getAllTeam(admin.getCid());
        logger.info("[管理员查询团队资源信息成功]");
        return new PageInfo<Team>(teams);
    }


    /**
     * 查询软件著作权信息
     *
     * @param session
     * @param pageno
     * @return 软件著作权信息
     */
    public PageInfo<Software> getAllSoftware(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        logger.info("[管理员查询软件著作权信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        PageHelper.startPage(pageno, 6);
        List<Software> softwares = adminMapper.getAllSoftware(admin.getCid());
        logger.info("[管理员查询团队资源信息成功]");
        return new PageInfo<Software>(softwares);
    }

    /**
     * 获取校企资源信息
     * admin用户取出session域中用户的id信息，不需要cid信息
     * root用户根据传递的cid信息查询相关信息
     *
     * @param user
     * @param cid
     * @param pageno
     * @return
     */
    public PageInfo<School> getAllSchool(User user, Integer cid, Integer pageno) {
        if (user == null && cid == null) {
            logger.info("[校企资源信息查询模块，参数传递异常]");
            CheckUtil.fail("参数异常");
        }
        logger.info("[管理员查询校企资源信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        //user为空时admin方查询
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        if (admin.getPower() == 2) {
            cid = admin.getCid();
            System.out.println(cid);
        }
        PageHelper.startPage(pageno, 6);
        List<School> schoolPageInfo = adminMapper.getAllSchool(cid);
        logger.info("[管理员查询校企资源信息成功,数据量{},管理员权限{}]", schoolPageInfo.size(), admin.getCid());
        return new PageInfo<School>(schoolPageInfo);
    }


    /**
     * 分页查询全部的需求信息
     *
     * @param user
     * @param pageno
     * @return
     */
    public PageInfo<Demand> getAllDemand(User user, Integer pageno) {
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询技术需求信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        PageHelper.startPage(pageno, 6);
        List<Demand> demands = adminMapper.getAllDemand();
        logger.info("[管理员查技术需求信息成功]");
        return new PageInfo<Demand>(demands);
    }


    /**
     * 分页查询全部需求方信息
     *
     * @param user
     * @param pageno
     * @return
     */
    public PageInfo<Needuser> getAllNeeduser(User user, Integer pageno) {
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        logger.info("[管理员查询技术需求方信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        PageHelper.startPage(pageno, 6);
        List<Needuser> needusers = adminMapper.getAllNeeduser();
        logger.info("[管理员查技术需求方信息成功]");
        return new PageInfo<Needuser>(needusers);
    }

    /**
     * 分页查询成果类别信息
     *
     * @param user
     * @param pageno
     * @return
     */
    public PageInfo<Achievement> getAllAchievement(User user, Integer pageno) {
        if (pageno == null || pageno <= 0) {
            pageno = 0;
        }
        logger.info("[管理员查询成果分类信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        PageHelper.startPage(pageno, 6);
        List<Achievement> achievements = adminMapper.getAllAchievement();
        logger.info("[管理员查技术需求方信息成功]");
        return new PageInfo<Achievement>(achievements);
    }

    public List<Achievement> getAchievements(User user) {
        logger.info("[管理员查询成果分类信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        List<Achievement> achievements = adminMapper.getAllAchievement();
        logger.info("[管理员查技术需求方信息成功]");
        return achievements;
    }


    /**
     * 工具  通过资源id查找该资源所属成果
     *
     * @param ids
     * @return
     */
    public Result getResultByIds(Integer ids) {
        cn.hsernos.pojo.Service service = serviceMapper.selectByIds(ids);
        return resultMapper.selectBySid(service.getSid());
    }


    /**
     * 修改审核信息
     *
     * @param patent
     */
    public void patentToexamine(Patent patent) {
        List<Result> result1 = resultMapper.getResultByPid(patent.getPid(), 1);
        if (result1.size() == 0) {
            Patent patent1 = new Patent();
            patent1.setPid(patent.getPid());
            patent1.setAudit(patent.getAudit());
            int row = patentMapper.updateByPrimaryKeySelective(patent1);
            if (row != 0) {
                logger.info("[专利信息审核信息修改成功]");
            } else {
                logger.info("[系统异常，审核信息修改失败]");
                CheckUtil.fail("系统异常，审核信息修改失败");
            }
        } else {
            logger.info("[专利已存在对接成果，禁止修改审核信息]");
            CheckUtil.fail("该专利已存在对接成功，禁止修改审核信息");
        }
    }


    /**
     * 获取专利详细信息
     *
     * @param pid
     * @return
     */
    public Patent getPatentInfo(Integer pid) {
        logger.info("[管理员查询专利详细信息]");
        return patentMapper.selectByPrimaryKey(pid);
    }


    /**
     * 专利信息删除
     *
     * @param pid
     */
    public void deletePatent(Integer pid) {
        List<Result> result1 = resultMapper.getResultByPid(pid, 1);
        if (result1.size() != 0) {
            logger.info("[已存在对接成果，禁止删除该专利信息]");
            CheckUtil.fail("[该专利已存在对接成果，禁止删除该专利信息]");
        } else {
            int row = patentMapper.deleteByPrimaryKey(pid);
            int row1 = serviceMapper.deleteByIdsAndRid(pid, 1);
            if (row != 0 && row1 != 0) {
                logger.info("[专利信息删除成功]");
            } else {
                logger.info("[专利信息删除失败]");
                CheckUtil.fail("系统异常，专利信息删除失败");
            }
        }
    }


    /**
     * 修改平台审核信息
     *
     * @param terrace
     */
    public void terraceToexamine(Terrace terrace) {
        List<Result> result1 = resultMapper.getResultByPid(terrace.getTid(), 5);
        if (result1.size() == 0) {
            Terrace terrace1 = new Terrace();
            terrace1.setAudit(terrace.getAudit());
            terrace1.setTid(terrace.getTid());
            int row = terraceMapper.updateByPrimaryKeySelective(terrace);
            if (row == 0) {
                logger.info("系统异常，修改失败");
                CheckUtil.fail("系统异常，修改失败");
            } else {
                logger.info("修改成功");
            }
        } else {
            logger.info("[该平台已存在对接成果，禁止修改审核信息]");
            CheckUtil.fail("该平台已存在对接成果，禁止修改审核信息");
        }
    }


    /**
     * 获取平台详细信息
     *
     * @param tid
     * @return 平台详细信息
     */
    public Terrace getTerraceInfo(Integer tid) {
        return terraceMapper.selectByPrimaryKey(tid);
    }


    /**
     * 修改平台相关信息
     *
     * @param terrace
     */
    public void updataTerrace(Terrace terrace) {
        List<Result> result1 = resultMapper.getResultByPid(terrace.getTid(), 5);
        if (result1.size() == 0) {
            int row = terraceMapper.updateByPrimaryKeySelective(terrace);
            if (row != 0) {
                logger.info("[平台信息修改成功]");
            } else {
                logger.info("[系统异常，平台信息修改失败]");
            }
        } else {
            logger.info("[该平台已存在对接成果信息,禁止修改平台相关信息]");
            CheckUtil.fail("[该平台已存在对接成果信息,禁止修改平台相关信息]");
        }
    }


    /**
     * 根据平台id删除平台相关信息
     *
     * @param tid
     */
    public void deleteTerrace(Integer tid) {
        List<Result> result1 = resultMapper.getResultByPid(tid, 5);
        if (result1.size() != 0) {
            logger.info("[已存在对接成果，禁止删除该专利信息]");
            CheckUtil.fail("[该专利已存在对接成果，禁止删除该专利信息]");
        }
        if (result1.size() == 0) {
            int row = terraceMapper.deleteByPrimaryKey(tid);
            int row1 = serviceMapper.deleteByIdsAndRid(tid, 5);
            if (row != 0 && row1 != 0) {
                logger.info("[平台信息删除成功]");
            } else {
                logger.info("[系统异常，平台信息删除失败]");
                CheckUtil.fail("[系统异常，平台信息删除失败]");
            }
        } else {
            logger.info("[该平台已存在对接成果，禁止删除平台信息]");
            CheckUtil.fail("该平台已存在对接成果信息，禁止删除该平台信息");
        }

    }


    /**
     * 修改团队审核信息
     *
     * @param team
     */
    public void teamToexamine(Team team) {
        List<Result> result1 = resultMapper.getResultByPid(team.getTid(), 4);
        if (result1.size() == 0) {
            Team team1 = new Team();
            team1.setAudit(team.getAudit());
            team1.setTid(team.getTid());
            int row = teamMapper.updateByPrimaryKeySelective(team1);
            if (row == 0) {
                logger.info("[系统异常，审核信息修改失败]");
                CheckUtil.fail("系统异常，审核信息修改失败");
            } else {
                logger.info("[审核信息修改成功]");
            }
        } else {
            logger.info("该团队已存在对接成果，禁止修改审核信息");
            CheckUtil.fail("该团队已存在对接成果，禁止修改审核信息");
        }
    }


    /**
     * 根据团队资源id值获取团队详细信息
     *
     * @param tid
     * @return
     */
    public Team getTeamInfo(Integer tid) {
        return teamMapper.selectByPrimaryKey(tid);
    }

    /**
     * 修改团队相关信息
     *
     * @param team
     */
    public void updataTeam(Team team) {
        List<Result> result1 = resultMapper.getResultByPid(team.getTid(), 4);
        if (result1.size() == 0) {
            int row = teamMapper.updateByPrimaryKeySelective(team);
            if (row == 0) {
                logger.info("[系统异常，团队信息更新失败]");
                CheckUtil.fail("系统异常，团队信息更新失败");
            } else {
                logger.info("[团队信息更新成功]");
            }
        } else {
            logger.info("[该团队已存在对接成果，禁止修改团队信息]");
            CheckUtil.fail("该团队已存在对接成果，禁止修改团队信息");
        }

    }

    /**
     * 删除团队信息  需要团队的tid信息
     *
     * @param tid
     */
    public void deleteTeam(Integer tid) {
        List<Result> result1 = resultMapper.getResultByPid(tid, 4);
        if (result1.size() != 0) {
            logger.info("[已存在对接成果，禁止删除该专利信息]");
            CheckUtil.fail("[该专利已存在对接成果，禁止删除该专利信息]");
        }
        if (result1.size() == 0) {
            int row = teamMapper.deleteByPrimaryKey(tid);
            int row1 = serviceMapper.deleteByIdsAndRid(tid, 4);
            if (row != 0 && row1 != 0) {
                logger.info("[删除团队信息成功]");
            } else {
                logger.info("[系统异常，删除团队信息失败]");
                CheckUtil.fail("系统异常，删除团队信息失败");
            }
        } else {
            logger.info("[该团队已存在对接成果，禁止修改团队信息]");
        }
    }


    /**
     * 修改软件著作资源审核信息
     *
     * @param software
     */
    public void softwareToexamine(Software software) {
        List<Result> result1 = resultMapper.getResultByPid(software.getSid(), 3);
        if (result1.size() == 0) {
            Software software1 = new Software();
            software1.setAudit(software.getAudit());
            software1.setSid(software.getSid());
            int row = softwareMapper.updateByPrimaryKeySelective(software);
            if (row == 0) {
                logger.info("[系统异常，软件著作资源审核信息修改失败]");
                CheckUtil.fail("系统异常，审核信息修改失败");
            } else {
                logger.info("[审核信息修改成功]");
            }
        } else {
            logger.info("[该资源存在对接成果，禁止修改审核信息]");
            CheckUtil.fail("该资源存在对接成果，禁止修改审核信息");
        }
    }


    /**
     * 根据资源id查询相关信息
     *
     * @param sid
     * @return
     */
    public Software getSoftware(Integer sid) {
        return softwareMapper.selectByPrimaryKey(sid);
    }


    /**
     * 修改软著信息
     *
     * @param software
     */
    public void updataSoftware(Software software) {
        List<Result> result1 = resultMapper.getResultByPid(software.getSid(), 3);
        if (result1.size() == 0) {
            int row = softwareMapper.updateByPrimaryKeySelective(software);
            if (row == 0) {
                logger.info("[系统异常，资源信息更新失败]");
                CheckUtil.fail("系统异常，资源信息更新失败");
            } else {
                logger.info("[资源更新成功]");
            }
        } else {
            logger.info("[该资源存在对接成果，禁止修改相关信息]");
            CheckUtil.fail("该资源存在对接成果，禁止修改相关信息");
        }
    }

    /**
     * 删除相关资源信息
     *
     * @param sid
     */
    public void deleteSoftware(Integer sid) {
        List<Result> result1 = resultMapper.getResultByPid(sid, 3);
        if (result1.size() == 0) {
            int row = softwareMapper.deleteByPrimaryKey(sid);
            int row1 = serviceMapper.deleteByIdsAndRid(sid, 3);
            if (row != 0 && row1 != 0) {
                logger.info("[删除软著信息成功]");
            } else {
                logger.info("[系统异常，删除软著信息失败]");
                CheckUtil.fail("系统异常，删除软著信息失败");
            }
        } else {
            logger.info("[该资源存在对接成果，禁止删除相关信息]");
            CheckUtil.fail("该资源存在对接成果，禁止删除相关信息");
        }
    }


//-------------------------

    /**
     * 审核资源信息
     *
     * @param school
     */
    public void schoolToexamine(School school) {
        List<Result> result1 = resultMapper.getResultByPid(school.getSid(), 2);
        if (result1.size() == 0) {
            School school1 = new School();
            school1.setSid(school.getSid());
            school1.setAudit(school.getAudit());
            int row = schoolMapper.updateByPrimaryKeySelective(school);
            if (row == 0) {
                logger.info("[系统异常，资源审核信息修改失败]");
                CheckUtil.fail("系统异常，资源审核信息修改失败");
            } else {
                logger.info("[审核信息修改成功]");
            }
        } else {
            logger.info("[该资源存在对接成果，禁止修改审核信息]");
            CheckUtil.fail("该资源存在对接成果，禁止修改审核信息");
        }
    }


    /**
     * 根据资源id查询相关信息
     *
     * @param sid
     * @return
     */
    public School getSchool(Integer sid) {
        return schoolMapper.selectByPrimaryKey(sid);
    }


    /**
     * 修改资源信息
     *
     * @param school
     */
    public void updataSchool(School school) {
        List<Result> result1 = resultMapper.getResultByPid(school.getSid(), 2);
        if (result1.size() == 0) {
            int row = schoolMapper.updateByPrimaryKeySelective(school);
            if (row == 0) {
                logger.info("[系统异常，资源信息更新失败]");
                CheckUtil.fail("系统异常，资源信息更新失败");
            } else {
                logger.info("[资源更新成功]");
            }
        } else {
            logger.info("[该资源存在对接成果，禁止修改相关信息]");
            CheckUtil.fail("该资源存在对接成果，禁止修改相关信息");
        }
    }

    /**
     * 删除相关资源信息
     *
     * @param sid
     */
    public void deleteSchool(Integer sid) {
        List<Result> result1 = resultMapper.getResultByPid(sid, 2);
        if (result1.size() != 0) {
            logger.info("[已存在对接成果，禁止删除该软著信息]");
            CheckUtil.fail("[该专利已存在对接成果，禁止删除该软著信息]");
        }
        if (result1.size() == 0) {
            int row = schoolMapper.deleteByPrimaryKey(sid);
            int row1 = serviceMapper.deleteByIdsAndRid(sid, 2);
            if (row != 0 && row1 != 0) {
                logger.info("[删除软著信息成功]");
            } else {
                logger.info("[系统异常，删除软著信息失败]");
                CheckUtil.fail("系统异常，删除软著信息失败");
            }
        }
    }


//-----------技术需求信息模块

    /**
     * 修改审核信息
     *
     * @param demand
     */
    public void demandToexamine(Demand demand) {
        List<Result> result1 = resultMapper.getResultByDid(demand.getDid());
        System.out.println("获取结果:" + result1.size());
        // CheckUtil.fail("获取结果"+result1.size());
        if (result1.size() == 0) {
            Demand demand1 = new Demand();
            demand1.setAudit(demand.getAudit());
            demand1.setDid(demand.getDid());
            int row = demandMapper.updateByPrimaryKeySelective(demand);
            if (row == 0) {
                logger.info("[系统异常，审核信息失败]");
                CheckUtil.fail("系统异常，信息审核失败");
            } else {
                logger.info("[信息审核成功]");
            }
        } else {
            logger.info("[该技术需求已经对接成功，禁止修改审核信息]");
            CheckUtil.fail("该技术需求已经对接成功，禁止修改审核信息");
        }
    }

    /**
     * @param did
     * @return
     */
    public Demand getDemandInfo(Integer did) {
        return demandMapper.selectByPrimaryKey(did);
    }



   /* public void updataDemand(Demand demand){
        List<Result> result1=resultMapper.getResultByPid(demand.getDid());
        if (result1.size()==0){
            int row=demandMapper.updateByPrimaryKeySelective(demand);
            if (row!=0){
                logger.info("信息修改成功");
            }else{
                logger.info("系统异常，信息修改失败");
                CheckUtil.fail("系统异常，信息修改失败");
            }
        }else{
            logger.info("[该技术需求已经对接成功，禁止修改该信息]");
            CheckUtil.fail("该技术需求已经对接成功，禁止修改该信息");
        }
    }*/

    /**
     * 删除技术需求信息
     *
     * @param did
     */
    public void deleteDemand(Integer did) {
        List<Result> result1 = resultMapper.getResultByDid(did);
        if (result1.size() == 0) {
            int row = demandMapper.deleteByPrimaryKey(did);
            int row1 = demandlistMapper.deteByDid(did);
            logger.info("[删除参数row:{},row1:{}]", row, row1);
            if (row != 0 && row1 != 0) {
                logger.info("信息删除成功");
            } else {
                logger.info("系统异常，信息删除失败");
                CheckUtil.fail("系统异常，信息删除失败");
            }
        } else {
            logger.info("[该技术需求已经对接成功，禁止删除该信息]");
            CheckUtil.fail("该技术需求已经对接成功，禁止删除该信息");
        }
    }


//--------技术需求方

    public void needuserToexamin(Needuser needuser) {
        List<Demandlist> demandlist = demandlistMapper.selectByNid(needuser.getNid());
        if (demandlist.size() == 0) {
            Needuser needuser1 = new Needuser();
            needuser1.setAudit(needuser.getAudit());
            needuser1.setNid(needuser.getNid());
            Byte power;
            if (needuser.getAudit() == 1) {
                power = 4;
                needuser1.setPower(power);
            } else {
                power = 0;
                needuser1.setPower(power);
            }
            int row = needuserMapper.updateByPrimaryKeySelective(needuser1);

            if (row == 0) {
                logger.info("[系统异常，信息审核失败]");
                CheckUtil.fail("系统异常，信息审核失败");
            } else {
                logger.info("信息审核成功");
            }

        } else {
            logger.info("该用户存在需求信息，禁止修改审核信息");
            CheckUtil.fail("该用户存在需求信息，禁止修改审核信息");
        }
    }

    public Needuser getNeeduser(Integer nid) {
        return needuserMapper.selectByPrimaryKey(nid);
    }

    public void updataNeeduser(Needuser needuser) {
        List<Demandlist> demandlist = demandlistMapper.selectByNid(needuser.getNid());
        if (demandlist.size() == 0) {
            int row = needuserMapper.updateByPrimaryKeySelective(needuser);
            if (row == 0) {
                logger.info("[系统异常，信息修改失败]");
                CheckUtil.fail("系统异常，信息修改失败");
            } else {
                logger.info("信息修改成功");
            }

        } else {
            logger.info("该用户存在需求信息，禁止修改相关信息");
            CheckUtil.fail("该用户存在需求信息，禁止修改相关信息");
        }
    }

    public void deleteNeeduser(Integer nid) {
        List<Demandlist> demandlist = demandlistMapper.selectByNid(nid);
        if (demandlist.size() == 0) {
            int row = needuserMapper.deleteByPrimaryKey(nid);
            if (row == 0) {
                logger.info("[系统异常，信息删除失败]");
                CheckUtil.fail("系统异常，信息删除失败");
            } else {
                logger.info("信息删除成功");
            }

        } else {
            logger.info("该用户存在需求信息，禁止删除相关信息");
            CheckUtil.fail("该用户存在需求信息，禁止删除相关信息");
        }
    }


    //---------成果分类信息

    /**
     * 添加
     *
     * @param achievement 分类
     */
    public void addAchievement(Achievement achievement) {
        String achievement1 = achievement.getAchievement();
        Achievement achievement2 = achievementMapper.getAchievementByachievement(achievement1);
        if (achievement2 != null) CheckUtil.fail("该分类已存在");
        logger.info("[添加]");
        achievementMapper.addAchievement(achievement);
    }


    public Achievement getAchievement(Integer aid) {
        return achievementMapper.selectByPrimaryKey(aid);
    }


    public void updataAchievement(Achievement achievement) {
        List<Result> result = resultMapper.selectByAid(achievement.getAid());
        if (result.size() == 0) {
            int row = achievementMapper.updateByPrimaryKeySelective(achievement);
            if (row == 0) {
                logger.info("系统异常，修改信息失败");
                CheckUtil.fail("系统异常，修改信息失败");
            } else {
                logger.info("修改信息成功");
            }
        } else {
            logger.info("该分类已存在对接成果，禁止修改该分类信息");
            CheckUtil.fail("该分类已存在对接成果，禁止修改该分类信息");
        }
    }


    public void deleteAchievement(Integer aid) {
        List<Result> result = resultMapper.selectByAid(aid);
        if (result.size() == 0) {
            int row = achievementMapper.deleteByPrimaryKey(aid);
            if (row == 0) {
                logger.info("系统异常，删除信息失败");
                CheckUtil.fail("系统异常，删除信息失败");
            } else {
                logger.info("删除信息成功");
            }
        } else {
            logger.info("该分类已存在对接成果，禁止删除该分类信息");
            CheckUtil.fail("该分类已存在对接成果，禁止删除该分类信息");
        }
    }


    //----------------对接成果信息

    /**
     * 获取所有对接成果信息
     *
     * @param pageno
     * @return
     */
    public PageInfo<Result> getAllResultByCid(User user, Integer cid, Integer pageno) {
        if (user == null && cid == null) {
            logger.info("[对接成果信息查询模块，参数传递异常]");
            CheckUtil.fail("参数异常");
        }
        logger.info("[管理员查询对接成果信息，参数[id:{},username{}]]", user.getId(), user.getUsername());
        if (pageno == null || pageno <= 0) {
            pageno = 1;
        }
        //user为空时admin方查询
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        if (admin.getPower() == 2) {
            cid = admin.getCid();
        }
        logger.info("[管理员查询对接成果信息]");
        PageHelper.startPage(pageno, 6);
        List<Result> results = resultMapper.selectAllResultByCid(cid);
        logger.info("[管理员查技术需求方信息成功]");
        return new PageInfo<Result>(results);
    }


    /**
     * 修改对接成果审核信息
     *
     * @param result
     */
    public void resultToexaimn(Result result) {
        logger.info("[管理员修改对接成果审核信息]");
        int row = resultMapper.updateByPrimaryKeySelective(result);
        if (row != 0) {
            logger.info("[对接成果信息审核成功]");
        } else {
            logger.info("[系统异常，对接成果信息审核失败");
            CheckUtil.fail("系统异常，信息修改审核失败，请重试");
        }
    }


    /**
     * 删除对接信息
     *
     * @param rid
     */
    public void deleteReslut(Integer rid) {
        logger.info("[管理员删除对接成果信息]");
        int row = resultMapper.deleteByPrimaryKey(rid);
        if (row == 0) {
            logger.info("[系统异常，删除对接成果失败，请重新再试]");
            CheckUtil.fail("系统异常，删除对接成果失败，请重新再试");
        } else {
            logger.info("[删除对接成果成功]");
        }
    }


    /**
     * 初始密码123456
     *
     * @param skilluser
     */
    public void addSkilluser(Skilluser skilluser) {
        logger.info("[管理员添加专业技术人员信息]");
        Byte power = 0;
        skilluser.setPower(power);
        skilluser.setPassword("123456");
        Skilluser skilluser1 = skilluserMapper.getSkilluserByUsername(skilluser.getUsername());
        if (skilluser1 != null) {
            logger.info("[工号已存在]");
            CheckUtil.fail("该工号已存在");
        }
        int row = skilluserMapper.insertSelective(skilluser);
    /*    Skilluser skilluser21=skilluserMapper.getSkilluserByUsername(skilluser.getUsername());
        System.out.println("----uid:"+skilluser21.getUid());
        int row2=skilluserMapper.addSkikkusers(skilluser21.getUid());*/
        Skilluser skilluser2 = skilluserMapper.getSkilluserByUsername(skilluser.getUsername());
        logger.info("根据技术人员工号查询是否为空：{}", skilluser2 == null);
        Skillusers skillusers = new Skillusers();
        skillusers.setUid(skilluser2.getUid());
        skillusers.setEmail("未完善");
        skillusers.setImg("hbwlxy/images/pic.png");
        skillusers.setSex("0");
        skillusers.setDegree("未完善");
        skillusers.setInfo("未完善");
        skillusers.setPhone("未完善");
        skillusers.setTitle("未完善");
        skillusers.setFid(1);
        Byte audit = 0;
        skillusers.setAudit(audit);
        logger.info("技术人员UID查询是否为空：{},uid为{}", skilluser2.getUid() == null, skilluser2.getUid());
        int row1 = skillusersMapper.addSkillusers(skillusers);
        if (row == 0 || row1 == 0) {
            logger.info("[系统异常，添加专业技术人员信息失败]");
            CheckUtil.fail("系统异常，添加专业技术人员信息失败");
        } else {
            logger.info("[添加转义技术人员信息成功]");
        }
    }


    public PageInfo<HserSkillUserVO> getAllSkilluser(User user, Integer pageno) {
        logger.info("[院级管理员查询技术人员信息：]");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        Integer cid = null;
        if (user.getPower() == 2) {
            cid = admin.getCid();
        }
        PageHelper.startPage(pageno, 6);
        List<HserSkillUserVO> skillusers = skilluserMapper.getAllSkilluser(cid);
        logger.info("[查询技术人员信息成功，数据量{}]", skillusers.size());
        return new PageInfo<HserSkillUserVO>(skillusers);
    }


    public PageInfo<HserSkillUserVO> getSkilluserByusername(Skilluser skilluser, User user, Integer pageno) {

        System.out.println(skilluser.getUid() + "---" + skilluser.getPassword() + "---" + skilluser.getName() + "---" +
                skilluser.getPower() + "---" + skilluser.getCid() + "---" + skilluser.getUsername());
        logger.info("[管理员查询技术人员信息，查询参数username：{}----name:{}]", skilluser.getUsername(), skilluser.getName());
        PageHelper.startPage(pageno, 6);
        Integer id = user.getId();
        Admin admin = adminMapper.selectByPrimaryKey(id);
        Integer cid = admin.getCid();
        Byte power = admin.getPower();
        if (power == 1) {
            skilluser.setCid(null);
        } else {
            skilluser.setCid(cid);
        }
        System.out.println("cid:" + cid);
        List<HserSkillUserVO> skillusers = skilluserMapper.getSkilluserByusername(skilluser);
        if (skillusers.size() == 0) {
            logger.info("[查无此人]");
            CheckUtil.fail("查无此人");
        }
        return new PageInfo<HserSkillUserVO>(skillusers);
    }


    public void skilluserToexaimn(HserSkillUserVO skillUserVO) {
        logger.info("[审核技术人员信息]");
        Skilluser skilluser1 = skilluserMapper.getSkilluserByUsername(skillUserVO.getUsername());
        Skillusers skillusers = new Skillusers();
        skillusers.setUid(skilluser1.getUid());
        skillusers.setAudit(skillUserVO.getAudit());
        skillusersMapper.updateByPrimaryKeySelective(skillusers);
        Skilluser skilluser = new Skilluser();
        skilluser.setUid(skilluser1.getUid());
        Byte power = 3;
        skilluser.setPower(power);
        int row = skilluserMapper.updateByPrimaryKeySelective(skilluser);
        if (row == 0) {
            logger.info("[系统异常，请重新再试]");
            CheckUtil.fail("系统异常，请重新再试");
        }
    }


    public void deleteSkillusers(HserSkillUserVO skillUserVO) {
        System.out.println("service 中的Username：" + skillUserVO.getUsername());
        Skilluser skilluser = skilluserMapper.getSkilluserByUsername(skillUserVO.getUsername());
        System.out.println("service 中的UID：" + skilluser.getUid());
        List<cn.hsernos.pojo.Service> serviceList = serviceMapper.getServiceByUid(skilluser.getUid());
        if (serviceList.size() != 0) {
            logger.info("[存在服务资源，禁止删除该技术人员]");
            CheckUtil.fail("存在服务资源，禁止删除该技术人员");
        }
        skilluserMapper.deleteByPrimaryKey(skilluser.getUid());
        skillusersMapper.deleteByPrimaryKey(skilluser.getUid());
    }


    /**
     * 查询统计数据
     *
     * @param uid  用户id
     * @param some 1：年:2：季:3：月
     * @return 统计数据
     */
    public ArrayList<HserStatisticsVO> getBySome(Integer uid, Integer some) {
        CheckUtil.notNull(uid, "参数为空");
        CheckUtil.notNull(some, "参数为空");
        logger.info("[获取统计数据，参数[cid:{},Some:{}]]", uid, some);
        Integer cid = adminMapper.selectByPrimaryKey(uid).getCid();
        if (cid == null) {
            CheckUtil.fail("非法");
        }
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

    /**
     * @param user        用户信息
     * @param oldpassword 旧密码
     * @param newpassword 新密码
     */
    public void updataPassword(User user, String oldpassword, String newpassword) {
        CheckUtil.notNull(oldpassword, "旧密码为空");
        CheckUtil.notNull(newpassword, "新密码为空");
        logger.info("[二级学院管理员修改密码");
        Admin admin = adminMapper.selectByPrimaryKey(user.getId());
        if (admin.getPassword().equals(oldpassword)) {
            Admin admin1 = new Admin();
            admin1.setUid(user.getId());
            admin1.setPassword(newpassword);
            int row = adminMapper.updateByPrimaryKeySelective(admin1);
            if (row != 0) {
                logger.info("[密码修改成功]");
            } else {
                logger.info("[系统异常，修改密码失败]");
                CheckUtil.fail("系统异常，修改密码失败");
            }
        } else {
            logger.info("[管理员旧密码填写错误]");
            CheckUtil.fail("旧密码填写错误");
        }

    }


    //按成果分类查询成果信息
    public PageInfo getResultByAid(Integer aid, Integer cid, Integer pageno) {
        logger.info("[按成果类别信息查询]");
        PageHelper.startPage(pageno, 6);
        List<Result> resultList = resultMapper.selectByAidAndCid(aid, cid);
        return new PageInfo<Result>(resultList);
    }


    public PageInfo getPatentServiceByName(User user, String name, Integer rid) {
        logger.info("[根据资源类别和资源名称查询相关资源信息，资源名称:{},资源类别id{}]", name, rid);
        PageHelper.startPage(6, 6);
        PatentExample patentExample = new PatentExample();
        PatentExample.Criteria criteria = patentExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        List<Patent> patents = patentMapper.selectByExample(patentExample);
        return new PageInfo<Patent>(patents);
    }


    public PageInfo getSchoolServiceByName(User user, String name, Integer rid) {
        logger.info("[根据资源类别和资源名称查询相关资源信息，资源名称:{},资源类别id{}]", name, rid);
        PageHelper.startPage(6, 6);
        SchoolExample schoolExample = new SchoolExample();
        SchoolExample.Criteria criteria = schoolExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        List<School> schools = schoolMapper.selectByExample(schoolExample);
        return new PageInfo<School>(schools);
    }


    public PageInfo getSoftwareServiceByName(User user, String name, Integer rid) {
        logger.info("[根据资源类别和资源名称查询相关资源信息，资源名称:{},资源类别id{}]", name, rid);
        PageHelper.startPage(6, 6);
        SoftwareExample softwareExample = new SoftwareExample();
        SoftwareExample.Criteria criteria = softwareExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        List<Software> softwares = softwareMapper.selectByExample(softwareExample);
        return new PageInfo<Software>(softwares);
    }


    public PageInfo getTeamServiceByName(User user, String name, Integer rid) {
        logger.info("[根据资源类别和资源名称查询相关资源信息，资源名称:{},资源类别id{}]", name, rid);
        PageHelper.startPage(6, 6);
        TeamExample teamExample = new TeamExample();
        TeamExample.Criteria criteria = teamExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        List<Team> teams = teamMapper.selectByExample(teamExample);
        return new PageInfo<Team>(teams);
    }


    public PageInfo getTerraceServiceByName(User user, String name, Integer rid) {
        logger.info("[根据资源类别和资源名称查询相关资源信息，资源名称:{},资源类别id{}]", name, rid);
        PageHelper.startPage(6, 6);
        TerraceExample terraceExample = new TerraceExample();
        TerraceExample.Criteria criteria = terraceExample.createCriteria();
        criteria.andNameLike("%" + name + "%");
        List<Terrace> terraces = terraceMapper.selectByExample(terraceExample);
        return new PageInfo<Terrace>(terraces);
    }


    public void resettingSkilluserPassword(String username) {
        logger.info("[重置技术人员密码]");
        String password = "123456";
        int row = adminMapper.resettingSkilluserPassword(username, password);
        if (row == 0) {
            CheckUtil.fail("系统异常，请重试");
            logger.info("系统异常，重置密码失败");
        }
        logger.info("重置密码成功");

    }


    public HserSkillUserVO getInfoByRidAndIds(Integer rid, Integer ids) {
        logger.info("查询技术方信息");
        return adminMapper.getInfoByRidAndIds(rid, ids);
    }


    public Needuser getNeeduserByDid(Integer did) {
        logger.info("查询需求方相关信息");
        return needuserMapper.getNeeduserByDid(did);
    }


}
