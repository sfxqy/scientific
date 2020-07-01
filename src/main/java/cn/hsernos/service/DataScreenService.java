package cn.hsernos.service;


import cn.hsernos.dao.DataScreenMapper;
import cn.hsernos.pojo.DataScreenVO;
import cn.hsernos.pojo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 数据大屏service层
 */

@Service
public class DataScreenService {

    private static final Logger logger = LoggerFactory.getLogger(DataScreenService.class);

    @Autowired
    private DataScreenMapper dataScreenMapper;

    //统计平台全部成果的经济效益和数量[数据大屏接口]
    public List<DataScreenVO> getAllBenefitsAndTurnover(Integer num) {
        logger.info("[统计全校对接成果排名，经济效益排名]");
        System.out.println("调用datascreenservice层");
        return dataScreenMapper.getAllBenefitsAndTurnover(num);
    }


    public List<DataScreenVO> getAllColleagebyTime(Integer rid, String time) {
        logger.info("[根据资源类别年份查询全校各个院别对接成果信息]");
        String starttime = time + "-1-1";
        String endtime = time + "-12-31";
        return dataScreenMapper.getAllColleagebyTime(rid, starttime, endtime);
    }


    public List<DataScreenVO> getResourceByMonth(Integer rid, String time) {
        logger.info("[根据资源类别年份查询全校该年对接成果数量]");
        String starttime = time + "-1-1";
        String endtime = time + "-12-31";
        return dataScreenMapper.getResourceByMonth(rid, starttime, endtime);
    }


    public List<DataScreenVO> getResultByYear(Integer cid) {
        logger.info("[查询近五年成果数据]");
        Calendar a = Calendar.getInstance();
        List<DataScreenVO> dataScreenVOList = new ArrayList<DataScreenVO>();
        int year = a.get(Calendar.YEAR);
        for (int i = 0; i < 5; i++) {
            int ye = year - i;
            String year1 = ye + "-01-01";
            String year2 = ye + "-12-31";
            DataScreenVO li = dataScreenMapper.getResultByYear(cid, year1, year2);
            li.setTimes(ye + "");
            dataScreenVOList.add(li);
        }
        List list = new ArrayList();
        for (DataScreenVO d : dataScreenVOList) {
            list.add(d.getTimes());
        }
        for (int i = 0; i < 5; i++) {
            if (list.contains(year - i)) {
                DataScreenVO dataScreenVO = new DataScreenVO();
                dataScreenVO.setTimes((year - i) + "");
                dataScreenVOList.add(dataScreenVO);
            }
        }

        return dataScreenVOList;
    }


    public List<ResourceVO> getResultByFiveYear(Integer cid) {
        return dataScreenMapper.getResultByFiveYear(cid);
    }


}
