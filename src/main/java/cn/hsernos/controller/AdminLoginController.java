package cn.hsernos.controller;


import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.pojo.Admin;
import cn.hsernos.service.AdminService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/***
 * @author xmq
 */

@RestController
@Controller
@CrossOrigin
public class AdminLoginController {


    private static final Logger logger = LoggerFactory.getLogger(AdminLoginController.class);

    @Autowired
    private AdminService adminService;


    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "username", value = "账号", required = true, dataType = "String")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "登录成功"),
            @ApiResponse(code = 1, message = "登录失败")
    })
    @RequestMapping(value = "/ladminlogin", method = RequestMethod.POST)
    public ResultBean adminlogin(HttpSession session, String username, String password) {
        System.out.println(username + "==========" + password);
        Admin admin = adminService.login(username, password, session);
        return new ResultBean(admin);
    }


    @ApiOperation(value = "登出")
    @RequestMapping(value = "/loginout", method = RequestMethod.GET)
    public void loginOut(HttpSession session) {
        logger.info("[管理员退出]");
        session.removeAttribute("user");
    }


}
