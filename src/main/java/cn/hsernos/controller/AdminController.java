package cn.hsernos.controller;


import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.pojo.*;
import cn.hsernos.service.AdminService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 * @author xmq
 * 信息审核接口提供信息资源id和审核是否通过对应的数值，封装为对象
 * 获取详细信息接口需要提供查询资源的id值
 * 修改资源信息需要提供修改后的数据，将数据封装为对象
 * 删除接口提供删除资源信息的id值
 */

@Api("院级管理员用户操作")
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;


    //统计页面数据  页面暂无此功能
    @PostMapping("/statistics")
    public ResultBean getBySome(HttpSession session, Integer timeClass) {
        return new ResultBean(adminService.getBySome(User.getId(session), timeClass));
    }


    @ApiOperation(value = "分页查询专利信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllPatent", method = RequestMethod.GET)
    public ResultBean getAllPatent(HttpSession session, Integer pageno) {
        PageInfo<Patent> paten = adminService.getAll(session, pageno);
        return new ResultBean(paten);
    }


    @ApiOperation(value = "分页查询平台资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllTerrace", method = RequestMethod.GET)
    public ResultBean getAllTerrace(HttpSession session, Integer pageno) {
        PageInfo<Terrace> terrace = adminService.getAllTerrace(session, pageno);
        return new ResultBean(terrace);
    }


    @ApiOperation(value = "分页查询团队资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllTeam", method = RequestMethod.GET)
    public ResultBean getAllTeam(HttpSession session, Integer pageno) {
        PageInfo<Team> teamPageInfo = adminService.getAllTeam(session, pageno);
        return new ResultBean(teamPageInfo);
    }


    @ApiOperation(value = "分页查询软著资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllSoftware", method = RequestMethod.GET)
    public ResultBean getAllSoftware(HttpSession session, Integer pageno) {
        System.out.println("pageno值:" + pageno);
        PageInfo<Software> softwarePageInfo = adminService.getAllSoftware(session, pageno);
        return new ResultBean(softwarePageInfo);
    }


    @ApiOperation(value = "分页查询查询校企资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllSchool", method = RequestMethod.GET)
    public ResultBean getAllSchool(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<School> schoolPageInfo = adminService.getAllSchool(user, null, pageno);
        return new ResultBean(schoolPageInfo);
    }


    @ApiOperation(value = "分页查询查询全部的需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllDemand", method = RequestMethod.GET)
    public ResultBean getAllDemand(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Demand> demandPageInfo = adminService.getAllDemand(user, pageno);
        return new ResultBean(demandPageInfo);
    }


    @ApiOperation(value = "分页查询需求方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllNeeduser", method = RequestMethod.GET)
    public ResultBean getAllNeeduser(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Needuser> needusers = adminService.getAllNeeduser(user, pageno);
        return new ResultBean(needusers);
    }


    @ApiOperation(value = "分页查询所有分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码序号", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllAchievement", method = RequestMethod.GET)
    public ResultBean getAllAchievement(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Achievement> achievementPageInfo = adminService.getAllAchievement(user, pageno);
        return new ResultBean(achievementPageInfo);
    }


    @ApiOperation(value = "审核专利信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "专利id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/patentToexamine", method = RequestMethod.PUT)
    public ResultBean patentToexamine(Patent patent) {
        adminService.patentToexamine(patent);
        return new ResultBean();
    }


    /*@ApiOperation(value = "获取单个专利的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "专利id", required = true , dataType = "int"),
    })*/
    //获取单个专利信息   页面无此功能
    @RequestMapping(value = "/getPatentInfo", method = RequestMethod.POST)
    public ResultBean getPatentInfo(Integer pid) {
        return new ResultBean(adminService.getPatentInfo(pid));
    }


    @ApiOperation(value = "删除专利信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pid", value = "专利id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/deletePatent", method = RequestMethod.DELETE)
    public ResultBean deletePatent(Integer pid) {
        System.out.println("删除专利信息pid：" + pid);
        adminService.deletePatent(pid);
        return new ResultBean();
    }


    @ApiOperation(value = "审核平台信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "平台id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/terraceToexamine", method = RequestMethod.PUT)
    public ResultBean terraceToexamine(Terrace terrace) {
        adminService.terraceToexamine(terrace);
        return new ResultBean();
    }


    @ApiOperation(value = "获取平台详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "平台id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getTerraceInfo", method = RequestMethod.GET)
    public ResultBean getTerraceInfo(Integer tid) {
        return new ResultBean(adminService.getTerraceInfo(tid));
    }


    //修改平台相关信息   页面暂无此功能
    @RequestMapping(value = "/updataTerrace")
    public ResultBean updataTerrace(Terrace terrace) {
        adminService.updataTerrace(terrace);
        return new ResultBean();
    }


    @ApiOperation(value = "删除平台信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "平台id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/deleteTerrace", method = RequestMethod.DELETE)
    public ResultBean deleteTerrace(Integer tid) {
        adminService.deleteTerrace(tid);
        return new ResultBean();
    }


    @ApiOperation(value = "审核团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "团队id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核信息", required = true, dataType = "int")
    })
    @RequestMapping(value = "/teamToexamine", method = RequestMethod.PUT)
    public ResultBean teamToexamine(Team team) {
        adminService.teamToexamine(team);
        return new ResultBean();
    }


    //获取单个团队信息   页面暂无此功能
    @PostMapping(value = "/getTeamInfo")
    public ResultBean getTeamInfo(Integer tid) {
        return new ResultBean(adminService.getTeamInfo(tid));
    }


    //修改团队信息   页面暂无此功能
    @PostMapping(value = "/updataTeam")
    public ResultBean updataTeam(Team team) {
        adminService.updataTeam(team);
        return new ResultBean();
    }


    @ApiOperation(value = "删除团队信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tid", value = "团队id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteTeam", method = RequestMethod.DELETE)
    public ResultBean deleteTeam(Integer tid) {
        adminService.deleteTeam(tid);
        return new ResultBean();
    }


    @ApiOperation(value = "审核软著信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "软著id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/softwareToexamine", method = RequestMethod.PUT)
    public ResultBean softwareToexamine(Software software) {
        adminService.softwareToexamine(software);
        return new ResultBean();
    }


    //获取单个软著信息  页面暂无此功能
    @PostMapping(value = "/getSoftwareInfo")
    public ResultBean get(Integer sid) {
        return new ResultBean(adminService.getSoftware(sid));
    }


    //修改软著信息    页面暂无此功能
    @PostMapping(value = "/updataSoftware")
    public ResultBean updata(Software software) {
        adminService.updataSoftware(software);
        return new ResultBean();
    }


    @ApiOperation(value = "删除软著信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "软著id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteSoftware", method = RequestMethod.DELETE)
    public ResultBean delete(Integer sid) {
        adminService.deleteSoftware(sid);
        return new ResultBean();
    }


//------校企

    @ApiOperation(value = "修改校企审核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "校企id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/schoolToexamine", method = RequestMethod.PUT)
    public ResultBean schoolToexamine(School school) {
        adminService.schoolToexamine(school);
        return new ResultBean();
    }


    //获取单个校企信息  页面暂无此功能
    @PostMapping(value = "/getSchool")
    public ResultBean getSchool(Integer sid) {
        return new ResultBean(adminService.getSchool(sid));
    }


    //修改校企信息  页面暂无此功能
    @PostMapping(value = "/updataSchool")
    public ResultBean updataSchool(School school) {
        adminService.updataSchool(school);
        return new ResultBean();
    }


    @ApiOperation(value = "删除校企信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "需求方需求id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteSchool", method = RequestMethod.DELETE)
    public ResultBean deleteSchool(Integer sid) {
        adminService.deleteSchool(sid);
        return new ResultBean();
    }


    @ApiOperation(value = "修改需求审核值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", value = "需求方需求id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/demandToexamine", method = RequestMethod.PUT)
    public ResultBean demandToexamine(Demand demand) {
        adminService.demandToexamine(demand);
        return new ResultBean();
    }


    //获取单个需求方需求信息   页面暂无此功能
    @PostMapping(value = "/getDemand")
    public ResultBean getDemand(Integer did) {
        return new ResultBean(adminService.getDemandInfo(did));
    }


    //修改需求方需求信息   页面暂无此功能
  /*  @PostMapping(value = "/updataDemand")
    public ResultBean updata(Demand demand){
        adminService.updataDemand(demand);
        return new ResultBean();
    }*/


    @ApiOperation(value = "删除需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", value = "需求方需求id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteDemand", method = RequestMethod.DELETE)
    public ResultBean deleteDemand(Integer did) {
        adminService.deleteDemand(did);
        return new ResultBean();
    }


//--------------技术需求方信息


    @ApiOperation(value = "修改需求方审核值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nid", value = "需求方id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/needuserToexamine", method = RequestMethod.PUT)
    public ResultBean needuserToexamine(Needuser needuser) {
        adminService.needuserToexamin(needuser);
        return new ResultBean();
    }

    //获取单个用户详细信息   页面暂无此功能项
    @PostMapping(value = "/getNeeduser")
    public ResultBean getNeeduser(Integer nid) {
        return new ResultBean(adminService.getNeeduser(nid));
    }


    //修改用户信息  页面暂无此功能项
    @PostMapping(value = "/updataNeeduser")
    public ResultBean updata(Needuser needuser) {
        adminService.updataNeeduser(needuser);
        return new ResultBean();
    }


    @ApiOperation(value = "删除需求方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nid", value = "需求方id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteNeeduser", method = RequestMethod.DELETE)
    public ResultBean deleteNeeduser(Integer nid) {
        adminService.deleteNeeduser(nid);
        return new ResultBean();
    }


//--------------成果分类信息


    @ApiOperation(value = "添加成果分类信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "achievement", value = "成果类别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "detail", value = "详细信息（描述）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/addAchievement", method = RequestMethod.POST)
    public ResultBean addAchievement(Achievement achievement) {
        adminService.addAchievement(achievement);
        return new ResultBean();
    }


    @ApiOperation(value = "获取单个成果类别详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAchievement", method = RequestMethod.GET)
    public ResultBean getAchievement(Integer aid) {
        return new ResultBean(adminService.getAchievement(aid));
    }


    @ApiOperation(value = "修改成果类别信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "achievement", value = "成果类别", required = true, dataType = "String"),
            @ApiImplicitParam(name = "detail", value = "详细信息（描述）", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updataAchievement", method = RequestMethod.PUT)
    public ResultBean updata(Achievement achievement) {
        adminService.updataAchievement(achievement);
        return new ResultBean();
    }


    @ApiOperation(value = "删除成果类别信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteAchievement", method = RequestMethod.DELETE)
    public ResultBean deleteAchievement(Integer aid) {
        adminService.deleteAchievement(aid);
        return new ResultBean();
    }


    @ApiOperation(value = "分页查询成果信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllResult", method = RequestMethod.GET)
    public ResultBean getAllResult(Integer pageno, HttpSession session) {
        System.out.println("pageno:" + pageno);
        User user = (User) session.getAttribute("user");
        PageInfo<Result> resultPageInfo = adminService.getAllResultByCid(user, null, pageno);
        System.out.println("数据量：" + resultPageInfo.getSize());
        return new ResultBean(resultPageInfo);
    }

    /**
     * @param aid
     * @param pageno
     * @return
     */
    @ApiOperation(value = "通过成果类别id查询成果信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getResultByAid", method = RequestMethod.POST)
    public ResultBean getResultByAid(Integer aid, Integer cid, Integer pageno) {
        PageInfo<Result> resultList = adminService.getResultByAid(aid, cid, pageno);
        return new ResultBean(resultList);
    }


    @ApiOperation(value = "修改成果审核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/resultToexaimn", method = RequestMethod.PUT)
    public ResultBean resultToexaimn(Result result) {
        adminService.resultToexaimn(result);
        return new ResultBean();
    }


    @ApiOperation(value = "删除成果信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteResult", method = RequestMethod.DELETE)
    public ResultBean deleteResult(Integer rid) {
        adminService.deleteReslut(rid);
        return new ResultBean();
    }


    @ApiOperation(value = "添加专业技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "工号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cid", value = "部门id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/addSkilluser", method = RequestMethod.POST)
    public ResultBean addSkilluser(Skilluser skilluser) {
        adminService.addSkilluser(skilluser);
        return new ResultBean();
    }


    @ApiOperation(value = "分页查询专业技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllSkilluser", method = RequestMethod.GET)
    public ResultBean getAllSkilluser(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<HserSkillUserVO> skilluserPageInfo = adminService.getAllSkilluser(user, pageno);
        return new ResultBean(skilluserPageInfo);
    }


    @ApiOperation(value = "审核技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "工号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "audit", value = "审核值", required = true, dataType = "int")
    })
    @RequestMapping(value = "/skilluserToexamin", method = RequestMethod.PUT)
    public ResultBean skilluserToexamin(HserSkillUserVO skilluser) {
        System.out.println(skilluser.getName() + "====审核值" + skilluser.getAudit());
        adminService.skilluserToexaimn(skilluser);
        return new ResultBean();
    }


    @ApiOperation(value = "删除技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "工号", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/deleteSkilluser", method = RequestMethod.DELETE)
    public ResultBean deleteSkilluser(HserSkillUserVO skillUserVO) {
        adminService.deleteSkillusers(skillUserVO);
        return new ResultBean();
    }


    @ApiOperation(value = "通过姓名查找技术人员[工号和姓名不能同时为空]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "username", value = "工号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String")
    })
    @RequestMapping(value = "/getSkilluserInfo", method = RequestMethod.POST)
    public ResultBean getSkilluserByusername(HttpSession session, Skilluser skilluser, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<HserSkillUserVO> skillUserVOPageInfo = adminService.getSkilluserByusername(skilluser, user, pageno);
        return new ResultBean(skillUserVOPageInfo);
    }


    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldpassword", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newpassword", value = "新密码", required = true, dataType = "String")
    })
    @RequestMapping(value = "/updataPassword", method = RequestMethod.POST)
    public ResultBean updataPassword(HttpSession session, String oldpassword, String newpassword) {
        User user = (User) session.getAttribute("user");
        adminService.updataPassword(user, oldpassword, newpassword);
        return new ResultBean();
    }


    @ApiOperation(value = "根据资源名称查询相关资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "资源名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "rid", value = "资源类型id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getServiceByName", method = RequestMethod.POST)
    public ResultBean getServiceByName(HttpSession session, String name, Integer rid) {
        User user = (User) session.getAttribute("user");
        Object service = null;
        PageInfo<Object> service1 = null;
        switch (rid) {
            case 1:
                service1 = adminService.getPatentServiceByName(user, name, rid);
                break;
            case 2:
                service1 = adminService.getSchoolServiceByName(user, name, rid);
                break;
            case 3:
                service1 = adminService.getSoftwareServiceByName(user, name, rid);
                break;
            case 4:
                service1 = adminService.getTeamServiceByName(user, name, rid);
                break;
            case 5:
                service1 = adminService.getTerraceServiceByName(user, name, rid);
                break;
        }
        return new ResultBean(service1);

    }

    @ApiOperation(value = "根据资源类型和资源编号查找资源对应技术人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "资源类型id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "ids", value = "资源id编号", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getSkilluserInfoByRidAndIds", method = RequestMethod.POST)
    public ResultBean getInfoByRidAndIds(Integer rid, Integer ids) {
        HserSkillUserVO hserSkillUserVO = adminService.getInfoByRidAndIds(rid, ids);
        return new ResultBean(hserSkillUserVO);
    }


    @ApiOperation(value = "根据需求id查询需求提供方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", value = "需求id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getNeeduserByDid", method = RequestMethod.POST)
    public ResultBean getNeeduserByDid(Integer did) {
        Needuser needuser = adminService.getNeeduserByDid(did);
        return new ResultBean(needuser);
    }


    @ApiOperation(value = "重置技术人员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "技术人员工号", required = true, dataType = "int")
    })
    @RequestMapping(value = "/resettingSkilluserPassword", method = RequestMethod.POST)
    public ResultBean resettingSkilluserPassword(String username) {
        System.out.println(username);
        adminService.resettingSkilluserPassword(username);
        return new ResultBean();
    }


}
