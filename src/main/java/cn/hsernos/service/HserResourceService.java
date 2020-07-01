package cn.hsernos.service;

import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.common.utils.FileUtil;
import cn.hsernos.dao.*;
import cn.hsernos.pojo.*;
import cn.hsernos.tools.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HserResourceService {
    private static final Logger logger = LoggerFactory.getLogger(HserResourceService.class);

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
    private ResultMapper resultMapper;

    /**
     * @param uid 用户id
     * @return 该用户的所有服务Id
     */
    public List<cn.hsernos.pojo.Service> getServicesId(Integer uid) {
        CheckUtil.notNull(uid, "参数不为空");
        logger.info("[查询id为<{}>的所有服务Id]", uid);
        List<cn.hsernos.pojo.Service> list = serviceMapper.selectByExample(null);
        logger.info("[查询结束]");
        return list;
    }

    public cn.hsernos.pojo.Service findService(Integer rid, Integer ids) {
        CheckUtil.notNull(rid, "参数不为空");
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[查询rid为<{}>,ids为<{}>的服务]", rid, ids);
        ServiceExample example = new ServiceExample();
        example.or().andRidEqualTo(rid).andIdsEqualTo(ids);
        cn.hsernos.pojo.Service service = Tool.getListFirst(serviceMapper.selectByExample(example));
        logger.info("[查询结束]");
        return service;
    }


    private void checkService(cn.hsernos.pojo.Service service, Integer uid) {
        if (service == null || (!service.getUid().equals(uid))) {
            logger.info("[更新失败]");
            CheckUtil.fail("非法访问");
        } else if (findResultBySid(service.getSid()) != null) {
            logger.info("[修改服务资源失败]");
            CheckUtil.fail("该资源已对接，不可修改或删除");
        }
    }


    private Result findResultBySid(Integer sid) {
        ResultExample example = new ResultExample();
        example.or().andSidEqualTo(sid);
        List<Result> list = resultMapper.selectByExample(example);
        return Tool.getListFirst(list);
    }


//----------------------------------以下为删除相关资源-------------------

    /**
     * @param ids 相关资源id
     * @param uid 用户id
     */
    public void delService(Integer rid, Integer ids, HttpSession session, Integer uid) {
        CheckUtil.notNull(ids, "参数不为空");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[删除服务资源中]");
        cn.hsernos.pojo.Service service = findService(rid, ids);
        checkService(service, uid);
        switch (rid) {
            case 1:
                delPatent(service.getIds(), session);
                break;
            case 2:
                delSchool(service.getIds(), session);
                break;
            case 3:
                delSoftware(service.getIds(), session);
                break;
            case 4:
                delTeam(service.getIds(), session);
                break;
            case 5:
                delTerrace(service.getIds(), session);
                break;
            default:
        }
        serviceMapper.deleteByPrimaryKey(service.getSid());
        logger.info("[删除服务资源成功]");
    }

    /**
     * @param ids     专利资源id
     * @param session HttpSession
     */
    private void delPatent(Integer ids, HttpSession session) {
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[删除专利资源中，参数[ids:{}]]", ids);
        String img = patentMapper.selectByPrimaryKey(ids).getImg();
        patentMapper.deleteByPrimaryKey(ids);
        FileUtil.deleteFile(session, img);
        logger.info("[删除完成]");
    }

    /**
     * @param ids     校企资源id
     * @param session HttpSession
     */
    private void delSchool(Integer ids, HttpSession session) {
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[删除校企资源中，参数[ids:{}]]", ids);
        String img = schoolMapper.selectByPrimaryKey(ids).getImg();
        schoolMapper.deleteByPrimaryKey(ids);
        FileUtil.deleteFile(session, img);
        logger.info("[删除完成]");
    }

    /**
     * @param ids     软著资源id
     * @param session HttpSession
     */
    private void delSoftware(Integer ids, HttpSession session) {
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[删除软著资源中，参数[ids:{}]]", ids);
        String img = softwareMapper.selectByPrimaryKey(ids).getImg();
        softwareMapper.deleteByPrimaryKey(ids);
        FileUtil.deleteFile(session, img);
        logger.info("[删除完成]");
    }

    /**
     * @param ids     团队资源id
     * @param session HttpSession
     */
    private void delTeam(Integer ids, HttpSession session) {
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[删除团队资源中，参数[ids:{}]]", ids);
        String img = teamMapper.selectByPrimaryKey(ids).getImg();
        teamMapper.deleteByPrimaryKey(ids);
        FileUtil.deleteFile(session, img);
        logger.info("[删除完成]");
    }

    /**
     * @param ids     平台资源id
     * @param session HttpSession
     */
    private void delTerrace(Integer ids, HttpSession session) {
        CheckUtil.notNull(ids, "参数不为空");
        logger.info("[删除平台资源中，参数[ids:{}]]", ids);
        String img = terraceMapper.selectByPrimaryKey(ids).getImg();
        terraceMapper.deleteByPrimaryKey(ids);
        FileUtil.deleteFile(session, img);
        logger.info("[删除完成]");
    }

//----------------------------------以下为添加相关资源-------------------

    /**
     * @param patent 专利资源的参数
     * @throws IOException 上传文件
     */
    public void addPatent(Patent patent, HttpSession session, Integer uid) throws IOException {
        List<Patent> patents = patentMapper.getPatenByPaten(patent);
        if (patents.size() != 0) {
            logger.info("[该专利资源已存在]");
            CheckUtil.fail("该专利资源已存在");
        }
        CheckUtil.notNull(uid, "用户id为空");
        //对象中参数arg中的三个属性必须为空,其他必须不为空
        CheckUtil.mustNullOtherOpposite(patent, "上传异常", "pid&audit");

        logger.info("[添加专利资源中,参数[{}]]", patent.toString());
        patentMapper.insertSelective(patent);
        cn.hsernos.pojo.Service service = new cn.hsernos.pojo.Service();
        service.setIds(patent.getPid());
        service.setRid(1);
        service.setUid(uid);
        serviceMapper.insertSelective(service);
        logger.info("[添加成功]");
    }

    /**
     * @param school 校企资源
     * @throws IOException 上传文件
     */
    public void addSchool(School school, HttpSession session, Integer uid) throws IOException {
        CheckUtil.notNull(uid, "用户id为空");
        //对象中参数arg中的三个属性必须为空,其他必须不为空
        CheckUtil.mustNullOtherOpposite(school, "上传异常", "sid&audit");
        logger.info("[添加校企资源中,参数[{}]]", school);
        schoolMapper.insertSelective(school);
        cn.hsernos.pojo.Service service = new cn.hsernos.pojo.Service();
        service.setIds(school.getSid());
        service.setRid(2);
        service.setUid(uid);
        serviceMapper.insertSelective(service);
        logger.info("[添加成功]");
    }

    /**
     * @param software 软著资源
     * @param session  HttpSession
     * @throws IOException 上传文件
     */
    public void addSoftware(Software software, HttpSession session, Integer uid) throws IOException {
        List<Software> softwares = softwareMapper.getSoftwareBySoftware(software);
        if (softwares.size() != 0) {
            logger.info("[该软著资源已存在]");
            CheckUtil.fail("该软著资源已存在");
        }
        CheckUtil.notNull(uid, "用户id为空");
        //对象中参数arg中的三个属性必须为空,其他必须不为空
        CheckUtil.mustNullOtherOpposite(software, "上传异常", "sid&audit");
        logger.info("[添加软著资源中,参数[{}]]", software);
        softwareMapper.insertSelective(software);
        cn.hsernos.pojo.Service service = new cn.hsernos.pojo.Service();
        service.setIds(software.getSid());
        service.setRid(3);
        service.setUid(uid);
        serviceMapper.insertSelective(service);
        logger.info("[添加成功]");
    }

    /**
     * @param team    团队资源
     * @param session HttpSession
     * @throws IOException 上传文件
     */
    public void addTeam(Team team, HttpSession session, Integer uid) throws IOException {
        List<Team> teams = teamMapper.getTeamByTeam(team);
        if (teams.size() != 0) {
            logger.info("[该团队资源已存在]");
            CheckUtil.fail("该团队资源已存在");
        }
        CheckUtil.notNull(uid, "用户id为空");
        //对象中参数arg中的三个属性必须为空,其他必须不为空
        CheckUtil.mustNullOtherOpposite(team, "上传异常", "tid&audit");
        logger.info("[添加团队资源中,参数[{}]]", team);
        teamMapper.insertSelective(team);
        cn.hsernos.pojo.Service service = new cn.hsernos.pojo.Service();
        service.setIds(team.getTid());
        service.setRid(4);
        service.setUid(uid);
        serviceMapper.insertSelective(service);
        logger.info("[添加成功]");
    }

    /**
     * @param terrace 平台资源
     * @param session HttpSession
     * @throws IOException 上传文件
     */
    public void addTerrace(Terrace terrace, HttpSession session, Integer uid) throws IOException {
        List<Terrace> terraces = terraceMapper.getTerraceByTerrace(terrace);
        if (terraces.size() != 0) {
            logger.info("[该平台资源已存在]");
            CheckUtil.fail("该平台资源已存在");
        }
        CheckUtil.notNull(uid, "用户id为空");
        //对象中参数arg中的三个属性必须为空,其他必须不为空
        CheckUtil.mustNullOtherOpposite(terrace, "上传异常", "tid&audit");
        logger.info("[添加平台资源中,参数[{}]]", terrace);
        terraceMapper.insertSelective(terrace);
        cn.hsernos.pojo.Service service = new cn.hsernos.pojo.Service();
        service.setIds(terrace.getTid());
        service.setRid(5);
        service.setUid(uid);
        serviceMapper.insertSelective(service);
        logger.info("[添加成功]");
    }

//----------------------------------以下为查询相关资源-------------------

    /**
     * @param pid 专利资源id
     * @param uid 用户id
     * @return 一条专利资源详情
     */
    public Patent getPatent(Integer pid, Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        CheckUtil.notNull(pid, "查询异常，参数为空");
        logger.info("[自我查询id为<{}>的专利资源中，]", pid);
        cn.hsernos.pojo.Service service = findService(1, pid);
        //查找不到，则为异常访问
        if (service != null && service.getUid().equals(uid)) {
            Patent patent = patentMapper.selectByPrimaryKey(pid);
            logger.info("[查询成功]");
            return patent;
        } else {
            logger.info("[查询失败]");
            CheckUtil.fail("非法访问");
            return null;
        }
    }

    public Patent getPatent(Integer pid) {
        CheckUtil.notNull(pid, "查询异常，参数为空");
        logger.info("[查询id为<{}>的专利资源中，]", pid);
        PatentExample example = new PatentExample();
        example.or()
                .andAuditGreaterThan((byte) 0)
                .andPidEqualTo(pid);
        return Tool.getListFirst(patentMapper.selectByExample(example));
    }


    /**
     * @param sid 校企资源id
     * @param uid 用户id
     * @return 一条校企资源详情
     */
    public School getSchool(Integer sid, Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        CheckUtil.notNull(sid, "查询异常，参数为空");
        logger.info("[自我查询id为<{}>的校企资源中，]", sid);
        cn.hsernos.pojo.Service service = findService(2, sid);
        if (service != null && service.getUid().equals(uid)) {
            School school = schoolMapper.selectByPrimaryKey(sid);
            logger.info("[查询成功]");
            return school;
        } else {
            logger.info("[查询失败]");
            CheckUtil.fail("非法访问");
            return null;
        }
    }

    public School getSchool(Integer sid) {
        CheckUtil.notNull(sid, "查询异常，参数为空");
        logger.info("[查询id为<{}>的校企资源中，]", sid);
        SchoolExample example = new SchoolExample();
        example.or()
                .andAuditGreaterThan((byte) 0)
                .andSidEqualTo(sid);
        return Tool.getListFirst(schoolMapper.selectByExample(example));
    }

    /**
     * @param sid 软著资源id
     * @param uid 用户id
     * @return 一条软著资源详情
     */
    public Software getSoftware(Integer sid, Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        CheckUtil.notNull(sid, "查询异常，参数为空");
        logger.info("[自我查询id为<{}>的软著资源中，]", sid);
        cn.hsernos.pojo.Service service = findService(3, sid);
        if (service != null && service.getUid().equals(uid)) {
            Software software = softwareMapper.selectByPrimaryKey(sid);
            logger.info("[查询成功]");
            return software;
        } else {
            logger.info("[查询失败]");
            CheckUtil.fail("非法访问");
            return null;
        }
    }

    public Software getSoftware(Integer sid) {
        CheckUtil.notNull(sid, "查询异常，参数为空");
        logger.info("[查询id为<{}>的软著资源中，]", sid);
        SoftwareExample example = new SoftwareExample();
        example.or()
                .andAuditGreaterThan((byte) 0)
                .andSidEqualTo(sid);
        return Tool.getListFirst(softwareMapper.selectByExample(example));
    }

    /**
     * @param tid 团队资源id
     * @return 一条团队资源详情
     */
    public Team getTeam(Integer tid, Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        CheckUtil.notNull(tid, "查询异常，参数为空");
        logger.info("[自我查询id为<{}>的团队资源中，]", tid);
        cn.hsernos.pojo.Service service = findService(4, tid);
        if (service != null && service.getUid().equals(uid)) {
            Team team = teamMapper.selectByPrimaryKey(tid);
            logger.info("[查询成功]");
            return team;
        } else {
            logger.info("[查询失败]");
            CheckUtil.fail("非法访问");
            return null;
        }
    }

    public Team getTeam(Integer tid) {
        CheckUtil.notNull(tid, "查询异常，参数为空");
        logger.info("[查询id为<{}>的团队资源中，]", tid);
        TeamExample example = new TeamExample();
        example.or()
                .andAuditGreaterThan((byte) 0)
                .andTidEqualTo(tid);
        return Tool.getListFirst(teamMapper.selectByExample(example));
    }

    /**
     * @param tid 平台资源id
     * @param uid 用户id
     * @return 一条平台资源详情
     */
    public Terrace getTerrace(Integer tid, Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        CheckUtil.notNull(tid, "查询异常，参数为空");

        logger.info("[自我查询id为<{}>的平台资源中，]", tid);
        cn.hsernos.pojo.Service service = findService(5, tid);
        if (service != null && service.getUid().equals(uid)) {
            Terrace terrace = terraceMapper.selectByPrimaryKey(tid);
            logger.info("[查询成功]");
            return terrace;
        } else {
            logger.info("[查询失败]");
            CheckUtil.fail("非法访问");
            return null;
        }
    }

    public Terrace getTerrace(Integer tid) {
        CheckUtil.notNull(tid, "查询异常，参数为空");
        logger.info("[查询id为<{}>的平台资源中，]", tid);
        TerraceExample example = new TerraceExample();
        example.or()
                .andAuditGreaterThan((byte) 0)
                .andTidEqualTo(tid);
        return Tool.getListFirst(terraceMapper.selectByExample(example));
    }


//----------------------------------以下为更新相关资源-------------------

    /**
     * @param patent 专利资源的参数
     * @throws IOException 上传文件
     */
    public void setPatent(Patent patent, HttpSession session, Integer uid) throws IOException {
        //对象中audit属性必须为空，其他随意
        CheckUtil.mustNull(patent.getAudit(), "数据异常");
        CheckUtil.notNull(patent.getPid(), "数据异常");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[开始更新，参数[{}]]", patent);
        cn.hsernos.pojo.Service service = findService(1, patent.getPid());
        checkService(service, uid);
        String delImg = null;
        if (patent.getImg() != null) {
            String img = patentMapper.selectByPrimaryKey(patent.getPid()).getImg();
            if (!img.equals(patent.getImg())) {
                delImg = img;
            }
        }
        patent.setAudit((byte) 0);
        patentMapper.updateByPrimaryKeySelective(patent);
        FileUtil.deleteFile(session, delImg);
        logger.info("[更新成功]");

    }

    /**
     * @param school 校企资源
     * @throws IOException 上传文件
     */
    public void setSchool(School school, HttpSession session, Integer uid) throws IOException {
        CheckUtil.mustNull(school.getAudit(), "数据异常");
        CheckUtil.notNull(school.getSid(), "数据异常");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[开始更新，参数[{}]]", school);
        cn.hsernos.pojo.Service service = findService(2, school.getSid());
        checkService(service, uid);
        String delImg = null;
        if (school.getImg() != null) {
            String img = schoolMapper.selectByPrimaryKey(school.getSid()).getImg();
            if (!img.equals(school.getImg())) {
                delImg = img;
            }
        }
        school.setAudit((byte) 0);
        schoolMapper.updateByPrimaryKeySelective(school);
        FileUtil.deleteFile(session, delImg);
    }

    /**
     * @param software 软著资源
     * @param session  HttpSession
     * @throws IOException 上传文件
     */
    public void setSoftware(Software software, HttpSession session, Integer uid) throws IOException {
        CheckUtil.mustNull(software.getAudit(), "数据异常");
        CheckUtil.notNull(software.getSid(), "数据异常");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[开始更新，参数[{}]]", software);
        cn.hsernos.pojo.Service service = findService(3, software.getSid());
        checkService(service, uid);
        String delImg = null;
        if (software.getImg() != null) {
            String img = softwareMapper.selectByPrimaryKey(software.getSid()).getImg();
            if (!img.equals(software.getImg())) {
                delImg = img;
            }
        }
        software.setAudit((byte) 0);
        softwareMapper.updateByPrimaryKeySelective(software);
        FileUtil.deleteFile(session, delImg);
        logger.info("[更新成功]");
    }

    /**
     * @param team    团队资源
     * @param session HttpSession
     * @throws IOException 上传文件
     */
    public void setTeam(Team team, HttpSession session, Integer uid) throws IOException {
        CheckUtil.mustNull(team.getAudit(), "数据异常");
        CheckUtil.notNull(team.getTid(), "数据异常");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[开始更新，参数[{}]]", team);

        cn.hsernos.pojo.Service service = findService(4, team.getTid());
        checkService(service, uid);
        String delImg = null;
        if (team.getImg() != null) {
            String img = softwareMapper.selectByPrimaryKey(team.getTid()).getImg();
            if (!img.equals(team.getImg())) {
                delImg = img;
            }
        }
        team.setAudit((byte) 0);
        teamMapper.updateByPrimaryKeySelective(team);
        FileUtil.deleteFile(session, delImg);
        logger.info("[更新成功]");
    }

    /**
     * @param terrace 平台资源
     * @param session HttpSession
     * @throws IOException 上传文件
     */
    public void setTerrace(Terrace terrace, HttpSession session, Integer uid) throws IOException {
        CheckUtil.mustNull(terrace.getAudit(), "数据异常");
        CheckUtil.notNull(terrace.getTid(), "数据异常");
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[开始更新，参数[{}]]", terrace);
        cn.hsernos.pojo.Service service = findService(5, terrace.getTid());
        checkService(service, uid);
        String delImg = null;
        if (terrace.getImg() != null) {
            String img = softwareMapper.selectByPrimaryKey(terrace.getTid()).getImg();
            if (!img.equals(terrace.getImg())) {
                delImg = img;
            }
        }
        terrace.setAudit((byte) 0);
        terraceMapper.updateByPrimaryKeySelective(terrace);
        FileUtil.deleteFile(session, delImg);
        logger.info("[更新成功]");
    }
}
