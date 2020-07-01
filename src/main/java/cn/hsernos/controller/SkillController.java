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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/***
 * @author SFX
 */
@RestController
@Controller
@RequestMapping("/skill")
@CrossOrigin
public class SkillController {

    @Autowired
    private HserResourceService resourceService;

    @Autowired
    private HserVoService voService;

    @Autowired
    private HserResultService resultService;

    @Autowired
    private HserDemandService demandService;

    @Autowired
    private HserSkillUserService skillUserService;

    @ApiOperation(value = "登录", notes = "根据账号密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "string"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string")
    })
    @PostMapping("/login")
    public ResultBean login(HttpSession session, String username, String password) {
        skillUserService.selectByPassword(username, password, session);
        return new ResultBean();
    }


    @ApiOperation(value = "添加专利资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "专利名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", required = true, dataType = "string"),
            @ApiImplicitParam(name = "username", value = "持有人姓名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "clazz", value = "专利类型", required = true, dataType = "string"),
            @ApiImplicitParam(name = "authorization", value = "专利授权号", required = true, dataType = "string"),
    })
    @RequestMapping(value = "/patent", method = RequestMethod.POST)
    public ResultBean addPatent(Patent patent, HttpSession session) throws IOException {
        resourceService.addPatent(patent, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改专利资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "专利资源id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "专利名称", dataType = "string"),
            @ApiImplicitParam(name = "username", value = "持有人姓名", dataType = "string"),
            @ApiImplicitParam(name = "clazz", value = "专利类型", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
            @ApiImplicitParam(name = "authorization", value = "专利授权号", dataType = "string"),
    })
    @RequestMapping(value = "/patent/{id}", method = RequestMethod.PUT)
    public ResultBean setPatent(@PathVariable Integer id, Patent patent, HttpSession session) throws IOException {
        patent.setPid(id);
        resourceService.setPatent(patent, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = " 校企资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img", value = "图片路径", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "项目名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "clazz", value = "项目类别", required = true, dataType = "string"),
    })
    @RequestMapping(value = "/school", method = RequestMethod.POST)
    public ResultBean addSchool(School school, HttpSession session) throws IOException {
        resourceService.addSchool(school, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改校企资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "校企资源id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "项目名称", dataType = "string"),
            @ApiImplicitParam(name = "clazz", value = "项目类别", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
    })
    @RequestMapping(value = "/school/{id}", method = RequestMethod.PUT)
    public ResultBean setSchool(@PathVariable Integer id, School school, HttpSession session) throws IOException {
        school.setSid(id);
        resourceService.setSchool(school, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "添加软著资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "img", value = "图片路径", required = true, dataType = "string"),
            @ApiImplicitParam(name = "name", value = "软著名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "username", value = "持有人姓名", required = true, dataType = "string"),
    })
    @RequestMapping(value = "/software", method = RequestMethod.POST)
    public ResultBean addSoftware(Software software, HttpSession session) throws IOException {
        resourceService.addSoftware(software, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改软著资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "软著资源id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "软著名称", dataType = "string"),
            @ApiImplicitParam(name = "username", value = "持有人姓名", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
    })
    @RequestMapping(value = "/software/{id}", method = RequestMethod.PUT)
    public ResultBean setSoftware(@PathVariable Integer id, Software software, HttpSession session) throws IOException {
        software.setSid(id);
        resourceService.setSoftware(software, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "添加团队资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "团队名", required = true, dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", required = true, dataType = "string"),
            @ApiImplicitParam(name = "username", value = "负责人", required = true, dataType = "string"),
            @ApiImplicitParam(name = "introduce", value = "团队介绍", required = true, dataType = "string"),
    })
    @RequestMapping(value = "/team", method = RequestMethod.POST)
    public ResultBean addTeam(Team team, HttpSession session) throws IOException {
        resourceService.addTeam(team, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改团队资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "团队资源id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "团队名", dataType = "string"),
            @ApiImplicitParam(name = "username", value = "负责人", dataType = "string"),
            @ApiImplicitParam(name = "introduce", value = "团队介绍", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
    })
    @RequestMapping(value = "/team/{id}", method = RequestMethod.PUT)
    public ResultBean setTeam(@PathVariable Integer id, Team team, HttpSession session) throws IOException {
        team.setTid(id);
        resourceService.setTeam(team, session, User.getId(session));
        return new ResultBean();
    }

    @ApiOperation(value = "添加平台资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "平台名称", required = true, dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", required = true, dataType = "string"),
            @ApiImplicitParam(name = "username", value = "负责人", required = true, dataType = "string"),
            @ApiImplicitParam(name = "introduce", value = "平台介绍", required = true, dataType = "string"),
    })
    @RequestMapping(value = "/terrace", method = RequestMethod.POST)
    public ResultBean addTerrace(Terrace terrace, HttpSession session) throws IOException {
        resourceService.addTerrace(terrace, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改平台资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "平台资源id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "平台名称", dataType = "string"),
            @ApiImplicitParam(name = "username", value = "负责人", dataType = "string"),
            @ApiImplicitParam(name = "introduce", value = "平台介绍", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "图片路径", dataType = "string"),
    })
    @RequestMapping(value = "/terrace/{id}", method = RequestMethod.PUT)
    public ResultBean setTerrace(@PathVariable Integer id, Terrace terrace, HttpSession session) throws IOException {
        terrace.setTid(id);
        resourceService.setTerrace(terrace, session, User.getId(session));
        return new ResultBean();
    }

    @ApiOperation(value = "删除服务资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "资源类型", required = true, dataType = "int"),
            @ApiImplicitParam(name = "sid", value = "对应资源id", required = true, dataType = "int")
    })
    @RequestMapping(value = "/service/{rid}/{sid}", method = RequestMethod.DELETE)
    public ResultBean delService(@PathVariable Integer rid, @PathVariable Integer sid, HttpSession session) {
        resourceService.delService(rid, sid, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "添加对接成果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sid", value = "服务id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "did", value = "需求id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "aid", value = "成果分类", required = true, dataType = "int"),
            @ApiImplicitParam(name = "title", value = "平台介绍", required = true, dataType = "string"),
            @ApiImplicitParam(name = "img", value = "照片", required = true, dataType = "string"),
            @ApiImplicitParam(name = "benefit", value = "经济效益", required = true, dataType = "string", paramType = "BigDecimal"),
            @ApiImplicitParam(name = "content", value = "服务内容", required = true, dataType = "string"),
            @ApiImplicitParam(name = "begin", value = "开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(name = "end", value = "结束时间", required = true, dataType = "Date"),
    })
    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ResultBean addResult(Result result, HttpSession session) {
        resultService.addResult(result, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "修改对接成果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "对接成果id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "sid", value = "服务id", dataType = "int"),
            @ApiImplicitParam(name = "did", value = "需求id", dataType = "int"),
            @ApiImplicitParam(name = "aid", value = "成果分类", dataType = "int"),
            @ApiImplicitParam(name = "title", value = "平台介绍", dataType = "string"),
            @ApiImplicitParam(name = "img", value = "照片", dataType = "string"),
            @ApiImplicitParam(name = "benefit", value = "经济效益", dataType = "string", paramType = "BigDecimal"),
            @ApiImplicitParam(name = "content", value = "服务内容", dataType = "string"),
            @ApiImplicitParam(name = "begin", value = "开始时间", dataType = "Date"),
            @ApiImplicitParam(name = "end", value = "结束时间", dataType = "Date"),
    })
    @RequestMapping(value = "/result/{id}", method = RequestMethod.PUT)
    public ResultBean setResult(@PathVariable Integer id, Result result, HttpSession session) {
        result.setRid(id);
        resultService.setResult(result, User.getId(session), session);
        return new ResultBean();
    }

    @ApiOperation(value = "删除对接成果")
    @ApiImplicitParam(name = "id", value = "对接成果id", required = true, dataType = "int")
    @RequestMapping(value = "/result/{id}", method = RequestMethod.DELETE)
    public ResultBean delResult(@PathVariable Integer id, HttpSession session) {
        resultService.delResult(id, User.getId(session), session);
        return new ResultBean();
    }


}
