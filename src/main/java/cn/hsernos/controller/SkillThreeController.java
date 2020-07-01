package cn.hsernos.controller;

import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.pojo.*;
import cn.hsernos.service.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/***
 * @author SFX
 */
@RestController
@Controller
@RequestMapping("/v3")
@CrossOrigin
public class SkillThreeController {
    private static final Logger logger = LoggerFactory.getLogger(SkillThreeController.class);
    @Autowired
    private HserVoService voService;

    @Autowired
    private HserSkillUserService skillUserService;


    @Autowired
    private HserResourceService resourceService;

    @Autowired
    private HserDemandService demandService;

    @Autowired
    private HserResultService resultService;

    /**
     * @return 当前用户详细信息
     */
    @ApiOperation("获取用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResultBean<HserSkillUserService> getUserInfo(HttpSession session) {

        User user = (User) session.getAttribute("user");
        logger.info("调用controller=====id为{}",user.getId());
        return new ResultBean(voService.selectSkillUserById(user.getId()));
    }

    @ApiOperation("修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword", value = "原密码", required = true, dataType = "string"),
            @ApiImplicitParam(name = "newPassword", value = "新密码", required = true, dataType = "string")
    })
    @PostMapping("/setPassword")
    public ResultBean setPassword(String oldPassword, String newPassword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        skillUserService.setPassword(oldPassword, newPassword, user.getId());
        return new ResultBean();
    }

    @ApiOperation("修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "部门id", dataType = "string"),
            @ApiImplicitParam(name = "name", value = "姓名", dataType = "string"),
            @ApiImplicitParam(name = "sex", value = "性别", dataType = "string"),
            @ApiImplicitParam(name = "title", value = "学位", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
            @ApiImplicitParam(name = "fid", value = "专业领域类别id", dataType = "string"),
            @ApiImplicitParam(name = "phone", value = "联系方式", dataType = "string"),
            @ApiImplicitParam(name = "email", value = "邮箱", dataType = "string"),
            @ApiImplicitParam(name = "degree", value = "学位", dataType = "string"),
            @ApiImplicitParam(name = "info", value = "基本信息简介", dataType = "string"),
    })
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResultBean setUser(Skilluser skilluser, Skillusers skillusers, HttpSession session) throws IOException {
       // logger.info("{}---{}---{}---{}---{}---{}");
        System.out.println(skillusers.getTitle());
        skillUserService.setUser(skilluser, skillusers, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "登出")
    @GetMapping("/logout")
    public ResultBean logout(HttpSession session) {
        session.removeAttribute("user");
        return new ResultBean();
    }

    @ApiOperation(value = "查找专利资源")
    @ApiImplicitParam(name = "id", value = "专利资源id", required = true, dataType = "int")
    @RequestMapping(value = "/patent/{id}", method = RequestMethod.GET)
    public ResultBean getPatent(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resourceService.getPatent(id, User.getId(session)));
    }

    @ApiOperation(value = "查找校企资源")
    @ApiImplicitParam(name = "id", value = "校企资源id", required = true, dataType = "int")
    @RequestMapping(value = "/school/{id}", method = RequestMethod.GET)
    public ResultBean getSchool(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resourceService.getSchool(id, User.getId(session)));
    }

    @ApiOperation(value = "查找软著资源")
    @ApiImplicitParam(name = "id", value = "软著资源id", required = true, dataType = "int")
    @RequestMapping(value = "/software/{id}", method = RequestMethod.GET)
    public ResultBean getSoftware(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resourceService.getSoftware(id, User.getId(session)));
    }

    @ApiOperation(value = "查找团队资源")
    @ApiImplicitParam(name = "id", value = "团队资源id", required = true, dataType = "int")
    @RequestMapping(value = "/team/{id}", method = RequestMethod.GET)
    public ResultBean getTeam(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resourceService.getTeam(id, User.getId(session)));
    }


    @ApiOperation(value = "查找平台资源")
    @ApiImplicitParam(name = "id", value = "平台资源id", required = true, dataType = "int")
    @RequestMapping(value = "/terrace/{id}", method = RequestMethod.GET)
    public ResultBean<Terrace> getTerrace(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resourceService.getTerrace(id, User.getId(session)));
    }

    @ApiOperation("获取详细服务资源列表")
    @RequestMapping(value = "/serviceInfo", method = RequestMethod.GET)
    public ResultBean<List<HserServiceVO>> getServices(HttpSession session) {
        return new ResultBean(voService.getServiceById(User.getId(session)));
    }

    @ApiOperation("获取服务资源列表")
    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public ResultBean<List<Service>> getServicesId(HttpSession session) {
        return new ResultBean(resourceService.getServicesId(User.getId(session)));
    }

    @ApiOperation(value = "查找对接成果")
    @ApiImplicitParam(name = "id", value = "对接成果id", required = true, dataType = "int")
    @RequestMapping(value = "/result/{id}", method = RequestMethod.GET)
    public ResultBean<Result> getResult(@PathVariable Integer id, HttpSession session) {
        return new ResultBean(resultService.getResult(id, User.getId(session)));
    }

    @ApiOperation(value = "查找需求列表", notes = "分页查找，一次<=10条记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int"),
            @ApiImplicitParam(name = "info", value = "查找信息", required = false, dataType = "String")
    })
    @RequestMapping(value = "/demands/{page}", method = RequestMethod.GET)
    public ResultBean<PageInfo<HserDemandVO>> getDemands(@PathVariable Integer page, String info) {
        return new ResultBean(demandService.getDemands(page, info));
    }

    @ApiOperation(value = "查找需求信息")
    @ApiImplicitParam(name = "id", value = "需求id", required = true, dataType = "int")
    @RequestMapping(value = "/demand/{id}", method = RequestMethod.GET)
    public ResultBean<Demand> getDemand(@PathVariable Integer id) {
        return new ResultBean(demandService.getDemand(id));
    }

    @ApiOperation(value = "查找详细成果信息")
    @ApiImplicitParam(name = "rid", value = "需求id", required = true, dataType = "int")
    @RequestMapping(value = "/resultVo/{rid}", method = RequestMethod.GET)
    public ResultBean<HserResultVO> findResultVo(@PathVariable Integer rid) {
        return new ResultBean(voService.findResultVO(rid));
    }


    @ApiOperation(value = "查找成果列表")
    @RequestMapping(value = "/resultList", method = RequestMethod.GET)
    public ResultBean<List<Result>> getResultList(HttpSession session) {
        return new ResultBean(resultService.getResultList(User.getId(session)));
    }

}
