package cn.hsernos.controller;


import cn.hsernos.common.beans.ResultBean;
import cn.hsernos.pojo.DataScreenVO;
import cn.hsernos.pojo.ResourceVO;
import cn.hsernos.service.DataScreenService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xmq
 * 数据大屏接口
 */
@RestController
@CrossOrigin
@RequestMapping("/data")
public class DataScreenController {


    @Autowired
    private DataScreenService dataScreenService;


    @ApiOperation(value = "统计平台全部成果的经济效益和数量[数据大屏接口,默认按经济效益降序排列]")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "num", value = "查询方式", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getAllBenefitsAndTurnover", method = RequestMethod.GET)
    public ResultBean getAllBenefitsAndTurnover(Integer num) {
        List<DataScreenVO> dataScreenVOList = dataScreenService.getAllBenefitsAndTurnover(num);
        return new ResultBean(dataScreenVOList);
    }

    @ApiOperation(value = "按年份和资源类别统计每个院对接成果数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "资源类别", required = true, dataType = "int"),
            @ApiImplicitParam(name = "time", value = "统计的年份", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getAllColleagebyTime", method = RequestMethod.POST)
    public ResultBean getAllColleagebyTime(Integer rid, String time) {
        List<DataScreenVO> dataScreenVOList = dataScreenService.getAllColleagebyTime(rid, time);
        return new ResultBean(dataScreenVOList);
    }


    @ApiOperation(value = "根据资源类别年份查询全校该年对接成果数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rid", value = "资源类别", required = true, dataType = "int"),
            @ApiImplicitParam(name = "time", value = "统计的年份", required = true, dataType = "String")
    })
    @RequestMapping(value = "/getResourceByMonth", method = RequestMethod.POST)
    public ResultBean getResourceByMonth(Integer rid, String time) {
        List<DataScreenVO> dataScreenVOList = dataScreenService.getResourceByMonth(rid, time);
        return new ResultBean(dataScreenVOList);
    }


    @ApiOperation(value = "查询近五年成果数据信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "学院id", required = false, dataType = "int"),
    })
    @RequestMapping(value = "/getResultByYear", method = RequestMethod.GET)
    public ResultBean getResultByYear(Integer cid) {
        List<DataScreenVO> dataScreenVOList = dataScreenService.getResultByYear(cid);
        return new ResultBean(dataScreenVOList);
    }

    @ApiOperation(value = "查询各种资源对接情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "学院id", required = true, dataType = "int"),
    })
    @RequestMapping(value = "/getResultByFiveYear", method = RequestMethod.POST)
    public ResultBean getResultByFiveYear(Integer cid) {
        List<ResourceVO> resourceList = dataScreenService.getResultByFiveYear(cid);
        System.out.println("数据长度：" + resourceList.size());
        return new ResultBean(resourceList);
    }


}
