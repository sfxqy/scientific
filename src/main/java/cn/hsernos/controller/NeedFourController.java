package cn.hsernos.controller;

import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.pojo.*;
import cn.hsernos.service.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/***
 * @author SFX
 */

@RestController
@CrossOrigin
@RequestMapping("/v4")
public class NeedFourController {

    @Autowired
    private HserNeedService needService;

    @Autowired
    private HserVoService voService;

    @Autowired
    private HserDemandService demandService;

    @Autowired
    private HserResourceService resourceService;

    @ApiOperation(value = "获取需求方信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResultBean<Needuser> getUser(HttpSession session) {
        return new ResultBean(needService.getUser(User.getId(session)));
    }

    @ApiOperation(value = "修改需求方信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = false, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "身份证", required = false, dataType = "String"),
            @ApiImplicitParam(name = "unit", value = "单位", required = false, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话号码", required = false, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "img", value = "头像", required = false, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "电子邮箱", required = false, dataType = "String"),
    })
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResultBean setUser(HttpSession session, Needuser needuser) {
        needService.setUser(User.getId(session), needuser);
        return new ResultBean();
    }

    @ApiOperation(value = "需求方修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/setPassword", method = RequestMethod.PUT)
    public ResultBean setPassword(String oldPassword, String newPassword, HttpSession session) {
        needService.setPassword(oldPassword, newPassword, User.getId(session));
        return new ResultBean();
    }

    @ApiOperation(value = "查找服务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "info", value = "查询信息", dataType = "String"),
    })
    @RequestMapping(value = "/service/{page}", method = RequestMethod.GET)
    public ResultBean<PageInfo<HserServiceVO>> getServiceList(@PathVariable Integer page, String info) {
        return new ResultBean(voService.findServiceList(page, info));
    }


    @ApiOperation(value = "需求方退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultBean logout(HttpSession session) {
        session.removeAttribute("user");
        return new ResultBean();
    }


    @ApiOperation(value = "查询需求信息列表")
    @RequestMapping(value = "/demand", method = RequestMethod.GET)
    public ResultBean<List<HserDemandVO>> getDemands(HttpSession session) {
        return new ResultBean(demandService.getDemandsByNid(User.getId(session)));
    }

    @ApiOperation(value = "查找需求信息", notes = "根据url的id查找需求信息")
    @ApiImplicitParam(name = "did", value = "需求id", required = true, dataType = "int")
    @RequestMapping(value = "/demand/{did}", method = RequestMethod.GET)
    public ResultBean<Demand> getDemand(@PathVariable Integer did, HttpSession session) {
        return new ResultBean(demandService.getDemand(did, User.getId(session)));
    }

    @ApiOperation(value = "查找已审核专利资源")
    @ApiImplicitParam(name = "id", value = "专利资源id", required = true, dataType = "int")
    @RequestMapping(value = "/patent/{id}", method = RequestMethod.GET)
    public ResultBean<Patent> getPatent(@PathVariable Integer id) {
        return new ResultBean(resourceService.getPatent(id));
    }

    @ApiOperation(value = "查找已审核校企资源")
    @ApiImplicitParam(name = "id", value = "校企资源id", required = true, dataType = "int")
    @RequestMapping(value = "/school/{id}", method = RequestMethod.GET)
    public ResultBean<School> getSchool(@PathVariable Integer id) {
        return new ResultBean(resourceService.getSchool(id));
    }

    @ApiOperation(value = "查找已审核软著资源")
    @ApiImplicitParam(name = "id", value = "软著资源id", required = true, dataType = "int")
    @RequestMapping(value = "/software/{id}", method = RequestMethod.GET)
    public ResultBean<Software> getSoftware(@PathVariable Integer id) {
        return new ResultBean(resourceService.getSoftware(id));
    }

    @ApiOperation(value = "查找已审核团队资源")
    @ApiImplicitParam(name = "id", value = "团队资源id", required = true, dataType = "int")
    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public ResultBean<Team> getTeam(@PathVariable Integer id) {
        return new ResultBean(resourceService.getTeam(id));
    }


    @ApiOperation(value = "查找已审核平台资源")
    @ApiImplicitParam(name = "id", value = "平台资源id", required = true, dataType = "int")
    @RequestMapping(value = "/terrace/{id}", method = RequestMethod.GET)
    public ResultBean<Terrace> getTerrace(@PathVariable Integer id) {
        return new ResultBean(resourceService.getTerrace(id));
    }


}
