package cn.hsernos.controller;

import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.common.beans.User;
import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.common.utils.FileUtil;
import cn.hsernos.pojo.*;
import cn.hsernos.service.HserDemandService;
import cn.hsernos.service.HserListService;
import cn.hsernos.service.HserResultService;
import cn.hsernos.service.HserVoService;
import cn.hsernos.tools.MailTool;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/***
 * @author SFX
 */
@SuppressWarnings({"ALL", "AlibabaClassMustHaveAuthor"})
@RestController
@CrossOrigin
public class EveryController {

    @Autowired
    private MailTool mailTool;

    @Autowired
    private HserListService hserListService;

    @Autowired
    private HserVoService voService;

    @Autowired
    private HserResultService resultService;

    @Autowired
    private HserDemandService demandService;

    @ApiOperation(value = "获取部门列表")
    @GetMapping("/college")
    public ResultBean<List<College>> getColleges() {
        return new ResultBean(hserListService.getColleges());
    }

    @ApiOperation(value = "获取一级专业领域列表")
    @RequestMapping(value = "/field", method = RequestMethod.GET)
    public ResultBean<List<Field>> getFieldList() {
        return new ResultBean(hserListService.getFieldList());
    }

    @ApiOperation(value = "获取二级专业领域列表", notes = "根据url的id来获取二级专业领域列表")
    @ApiImplicitParam(name = "id", value = "一级专业领域ID", required = true, dataType = "int", paramType = "path")
    @RequestMapping(value = "/fields/{id}", method = RequestMethod.GET)
    public ResultBean<List<Fields>> getFieldsListByField(@PathVariable Integer id) {
        return new ResultBean(hserListService.getFieldsListById(id));
    }

    @ApiOperation(value = "获取行动列表")
    @RequestMapping(value = "/achievement", method = RequestMethod.GET)
    public ResultBean<List<Achievement>> getAchievements() {
        return new ResultBean(hserListService.getAchievements());
    }

    @ApiOperation(value = "获取资源类别列表")
    @RequestMapping(value = "resource", method = RequestMethod.GET)
    public ResultBean<List<Resource>> getResources() {
        return new ResultBean(hserListService.getResources());
    }

    /**
     * @param session
     * @param email
     * @return
     */
    @ApiOperation(value = "发送邮件验证码")
    @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, paramType = "query", dataType = "String")
    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResultBean email(HttpSession session, String email) {
        CheckUtil.notEmpty("email", "邮箱不能为空");
        String vcode = FileUtil.getRandom(6);
        session.setAttribute("vcode", vcode);
        session.setAttribute("email", email);
        mailTool.sendHtml("襄阳对接平台", "验证码为:" + vcode, email);
        return new ResultBean();
    }


    @ApiOperation(value = "上传图片", notes = "返回路径")
    @ApiImplicitParam(name = "pic", value = "图片文件", paramType = "form", dataType = "MultipartFile")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultBean<String> upload(MultipartFile pic, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String path = "img/" + User.getPower(session) + "-" + User.getId(session) + "/";
            String img = FileUtil.saveImgFile(session, pic, path);
            return new ResultBean(img);
        } else {
            CheckUtil.fail("上传失败,无权限");
            return null;
        }
    }

    @ApiOperation(value = "成果统计-获取年份表")
    @RequestMapping(value = "/statistics/year", method = RequestMethod.GET)
    public ResultBean<List<String>> getYears() {
        return new ResultBean(voService.getYears());
    }

    @ApiOperation(value = "成果统计-按年统计")
    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ResultBean<List<HserStatisticsVO>> getYearInfo() {
        return new ResultBean(voService.getYearsInfo());
    }

    @ApiOperation(value = "成果统计-根据年份按月统计")
    @ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "String")
    @RequestMapping(value = "/statistics/{year}", method = RequestMethod.GET)
    public ResultBean<List<HserStatisticsVO>> getAllyear(@PathVariable String year) {
        return new ResultBean(voService.getMonthInfoByYear(year));
    }

    @ApiOperation(value = "成果统计-根据年份按资源分类统计")
    @ApiImplicitParam(name = "year", value = "年份", required = true, dataType = "String")
    @RequestMapping(value = "/statistics/resource/{year}", method = RequestMethod.GET)
    public ResultBean<List<HserStatisticsVO>> getGroupByRid(@PathVariable String year) {
        return new ResultBean(voService.getGroupByRid(year));
    }

    @ApiOperation(value = "查找已审核成果列表")
    @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "int")
    @RequestMapping(value = "/resultList/{page}", method = RequestMethod.GET)
    public ResultBean<List<Result>> getResultList(@PathVariable Integer page) {
        return new ResultBean(resultService.getResultList(page, null));
    }

    @ApiOperation(value = "查找已审核的对接成果")
    @ApiImplicitParam(name = "rid", value = "对接成果id", required = true, dataType = "int")
    @RequestMapping(value = "/result/{rid}", method = RequestMethod.GET)
    public ResultBean<HserResultVO> getResult(@PathVariable Integer rid) {
        HserResultVO resultVO = voService.findResultVO(rid);
        if (resultVO.getAudit() <= 0) {
            resultVO = null;
        }
        return new ResultBean(resultVO);
    }

    @ApiOperation(value = "查找已审核需求列表", notes = "分页查找，一次<=10条记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int"),
            @ApiImplicitParam(name = "info", value = "查找信息", required = false, dataType = "String")
    })
    @RequestMapping(value = "/demands/{page}", method = RequestMethod.GET)
    public ResultBean<PageInfo<HserDemandVO>> getDemands(@PathVariable Integer page, String info) {
        return new ResultBean(demandService.getDemands(page, info));
    }

    @ApiOperation(value = "查找已审核需求信息")
    @ApiImplicitParam(name = "id", value = "需求id", required = true, dataType = "int")
    @RequestMapping(value = "/demand/{id}", method = RequestMethod.GET)
    public ResultBean<Demand> getDemand(@PathVariable Integer id) {
        return new ResultBean(demandService.getDemandLimitAudit(id));
    }

}
