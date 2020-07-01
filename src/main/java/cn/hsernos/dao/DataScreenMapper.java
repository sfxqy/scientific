package cn.hsernos.dao;


import cn.hsernos.pojo.DataScreenVO;
import cn.hsernos.pojo.ResourceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DataScreenMapper {


    List<DataScreenVO> getAllBenefitsAndTurnover(@Param("num") Integer num);

    List<DataScreenVO> getAllColleagebyTime(@Param("rid") Integer rid, @Param("starttime") String starttime, @Param("endtime") String endtime);

    List<DataScreenVO> getResourceByMonth(@Param("rid") Integer rid, @Param("starttime") String starttime, @Param("endtime") String endtime);

    DataScreenVO getResultByYear(@Param("cid") Integer cid, @Param("starttime") String starttime, @Param("endtime") String endtime);

    List<ResourceVO> getResultByFiveYear(@Param("cid") Integer cid);
}
