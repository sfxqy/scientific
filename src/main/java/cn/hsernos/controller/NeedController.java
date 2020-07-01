package cn.hsernos.controller;

import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.pojo.Demand;
import cn.hsernos.pojo.HserDemandVO;
import cn.hsernos.pojo.Needuser;
import cn.hsernos.service.HserDemandService;
import cn.hsernos.service.HserNeedService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/***
 * @author SFX
 */
@SuppressWarnings({"ALL", "AlibabaClassMustHaveAuthor"})
@RestController
@CrossOrigin
@RequestMapping("/need")
public class NeedController {

    @Autowired
    private HserDemandService demandService;

    @Autowired
    private HserNeedService needService;

    @ApiOperation(value = "需求方登录", notes = "根据账号和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "int")
    })
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultBean login(HttpSession session, String username, String password) {
        needService.login(session, username, password);
        return new ResultBean();
    }


    @ApiOperation(value = "需求方注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "number", value = "身份证", required = true, dataType = "String"),
            @ApiImplicitParam(name = "unit", value = "单位", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "电话号码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "img", value = "头像", required = true, dataType = "String"),
            @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vcode", value = "验证码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultBean register(Needuser needuser, String vcode, HttpSession session) {
        needService.register(needuser, vcode, session);
        return new ResultBean();
    }

    @ApiOperation(value = "需求方找回密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, dataType = "String"),
            @ApiImplicitParam(name = "vcode", value = "验证码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public ResultBean findPassword(String email, String password, String vcode, HttpSession session) {
        needService.findPassword(email, password, vcode, session);
        return new ResultBean();
    }


    @ApiOperation(value = "添加需求信息", notes = "根据Demand对象添加需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "需求名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "describe", value = "需求描述", required = true, dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "联系人方式", required = true, dataType = "String"),
            @ApiImplicitParam(name = "detail", value = "需求具体信息", required = true, dataType = "String"),
            @ApiImplicitParam(name = "img", value = "图片文件", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "联系人姓名", required = true, dataType = "String")
    })
    @RequestMapping(value = "/demand", method = RequestMethod.POST)
    public ResultBean addDemand(Demand demand, HttpSession session) throws IOException {
        demandService.addDemand(demand, User.getId(session));
        return new ResultBean();
    }

    @ApiOperation(value = "更新需求信息", notes = "根据url的id更新需求信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "did", value = "需求id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "name", value = "需求名", dataType = "String"),
            @ApiImplicitParam(name = "describe", value = "需求描述", dataType = "String"),
            @ApiImplicitParam(name = "phone", value = "联系人方式", dataType = "String"),
            @ApiImplicitParam(name = "img", value = "图片文件", dataType = "String"),
            @ApiImplicitParam(name = "detail", value = "需求具体信息", dataType = "String"),
            @ApiImplicitParam(name = "username", value = "联系人姓名", dataType = "String")
    })
    @RequestMapping(value = "/demand/{did}", method = RequestMethod.PUT)
    public ResultBean setDemand(@PathVariable Integer did, Demand demand, HttpSession session)
            throws IOException {
        demand.setDid(did);
        demandService.setDemand(demand, session, User.getId(session));
        return new ResultBean();
    }


    @ApiOperation(value = "删除需求信息", notes = "根据url的id删除需求信息")
    @ApiImplicitParam(name = "did", value = "需求id", required = true, dataType = "int")
    @RequestMapping(value = "/demand/{did}", method = RequestMethod.DELETE)
    public ResultBean delDemand(@PathVariable Integer did, HttpSession session) {
        demandService.delDemand(did, User.getId(session), session);
        return new ResultBean();
    }

}
