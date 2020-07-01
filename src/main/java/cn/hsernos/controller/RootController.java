package cn.hsernos.controller;


import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.pojo.*;
import cn.hsernos.service.AdminService;
import cn.hsernos.service.RootService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/***
 * @author SFX
 */

@RestController
@Controller
@RequestMapping("/root")
@CrossOrigin
public class RootController {

    @Autowired
    public RootService rootService;

    @Autowired
    public AdminService adminService;


    //页面统计数据  暂无此页面
    @PostMapping("/statistics")
    public ResultBean getBySome(Integer cid, Integer timeClass) {
        return new ResultBean(rootService.getBySome(cid, timeClass));
    }


    @ApiOperation(value = "分页查询专利信息[若cid为空查询全校专利信息，若cid不为空则根据cid查询对应院别专利信息]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllPatent", method = RequestMethod.POST)
    public ResultBean getAllPatent(Integer cid, Integer pageno) {
        PageInfo<Patent> paten = rootService.getAll(cid, pageno);
        return new ResultBean(paten);
    }


    @ApiOperation(value = "分页查询平台资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllTerrace", method = RequestMethod.POST)
    public ResultBean getAllTerrace(Integer cid, Integer pageno) {
        PageInfo<Terrace> terrace = rootService.getAllTerrace(cid, pageno);
        return new ResultBean(terrace);
    }


    @ApiOperation(value = "分页查询团队资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllTeam", method = RequestMethod.POST)
    public ResultBean getAllTeam(HttpSession session, Integer cid, Integer pageno) {
        PageInfo<Team> teams = rootService.getAllTeam(session, cid, pageno);
        return new ResultBean(teams);
    }


    @ApiOperation(value = "分页查询软著资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "getAllSoftware", method = RequestMethod.POST)
    public ResultBean getAllSoftware(HttpSession session, Integer cid, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Software> software = rootService.getAllSoftware(cid, pageno, user.getId());
        return new ResultBean(software);
    }


    @ApiOperation(value = "分页查询校企资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "院别id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllSchool", method = RequestMethod.POST)
    public ResultBean getAllSchool(HttpSession session, Integer cid, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<School> school = adminService.getAllSchool(user, cid, pageno);
        return new ResultBean(school);
    }


    @ApiOperation(value = "分页查询需求方需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllDemand", method = RequestMethod.POST)
    public ResultBean getAllDemand(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Demand> demandPageInfo = adminService.getAllDemand(user, pageno);
        return new ResultBean(demandPageInfo);
    }


    @ApiOperation(value = "分页查询需求方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllNeeduser", method = RequestMethod.GET)
    public ResultBean getAllNeeduser(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Needuser> needusers = adminService.getAllNeeduser(user, pageno);
        return new ResultBean(needusers);
    }


    @ApiOperation(value = "分页查询成果类别信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllAchievement", method = RequestMethod.GET)
    public ResultBean getAllAchievement(HttpSession session, Integer pageno) {
        User user = (User) session.getAttribute("user");
        PageInfo<Achievement> achievementPageInfo = adminService.getAllAchievement(user, pageno);
        return new ResultBean(achievementPageInfo);
    }


    @ApiOperation(value = "查询成果类别信息[xx行动]")
    @RequestMapping(value = "/getAchievement", method = RequestMethod.GET)
    public ResultBean getAchievement(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Achievement> achievementPageInfo = adminService.getAchievements(user);
        return new ResultBean(achievementPageInfo);
    }


    @ApiOperation(value = "获取全校院别信息")
    @RequestMapping(value = "/getAllCollege", method = RequestMethod.GET)
    public ResultBean getAllCollege() {
        List<College> collegeList = rootService.getAllCollege();
        return new ResultBean(collegeList);
    }


    @ApiOperation(value = "分页查询管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/getAllAdmin", method = RequestMethod.GET)
    public ResultBean getAllAdmin(Integer pageno) {
        PageInfo<Admin> adminPageInfo = rootService.getAllAdmin(pageno);
        return new ResultBean(adminPageInfo);
    }


    @ApiOperation(value = "重置管理员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "管理员id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/resettingPassword", method = RequestMethod.POST)
    public ResultBean resettingPassword(Integer uid) {
        rootService.resettingPassword(uid);
        return new ResultBean();
    }


    @ApiOperation(value = "删除管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "管理员id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/deleteAdmin", method = RequestMethod.POST)
    public ResultBean deleteAdmin(Integer uid) {
        rootService.deleteAdmin(uid);
        return new ResultBean();
    }


    @ApiOperation(value = "添加管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "cid", value = "学院id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public ResultBean addAdmin(String username, Integer cid) {
        rootService.addAdmin(username, cid);
        return new ResultBean();
    }


    @ApiOperation(value = "分页查询对接成果信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "cid", value = "学院id", required = false, dataType = "int"),
            @ApiImplicitParam(name = "aid", value = "成果类别id", required = false, dataType = "int")
    })
    @RequestMapping(value = "/getAllResults", method = RequestMethod.POST)
    public ResultBean getAllResult(Integer pageno, Integer cid, Integer aid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        PageInfo<Result> resultPageInfo = rootService.getAllResultByCidAndAid(cid, aid, pageno);
        return new ResultBean(resultPageInfo);
    }


    @ApiOperation(value = "查询成果对应的技术资源信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getResultSource", method = RequestMethod.POST)
    public ResultBean getResultSource(Integer rid) {
        Object source = rootService.getResultSource(rid);
        return new ResultBean(source);
    }


    @ApiOperation(value = "查询成果对应的技术资源方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getResultSkilluser", method = RequestMethod.POST)
    public ResultBean getResultSkilluser(Integer rid) {
        HserSkillUserVO hserSkillUserVO = rootService.getResultSkilluser(rid);
        return new ResultBean(hserSkillUserVO);
    }

    @ApiOperation(value = "查询成果对应的需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getResultDemand", method = RequestMethod.POST)
    public ResultBean getResultDemand(Integer rid) {
        Demand demand = rootService.getResultDemand(rid);
        return new ResultBean(demand);
    }


    @ApiOperation(value = "查询成果信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "成果id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getResult", method = RequestMethod.POST)
    public ResultBean getResult(Integer rid) {
        return new ResultBean(rootService.getResult(rid));
    }


}
