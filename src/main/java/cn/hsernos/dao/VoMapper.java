package cn.hsernos.dao;


import cn.hsernos.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;



@Repository
public interface VoMapper {
    /**
     * @param uid 主键
     * @return 技术方详细信息
     */
    HserSkillUserVO selectSkillUserById(Integer uid);

    ArrayList<HserServiceVO> getServiceById(Integer uid);

    ArrayList<HserDemandVO> getDemandVo(Integer nid);

    ArrayList<HserSkillListVO> getSkillList(@Param("info") String info);

    ArrayList<HserServiceVO> findServiceList(@Param("info") String info);

    HserResultVO findResultVo(Integer rid);

    ArrayList<HserStatisticsVO> getByYear(@Param("cid") Integer cid);

    ArrayList<HserStatisticsVO> getByQuarter(@Param("cid") Integer cid);

    ArrayList<HserStatisticsVO> getByMonth(@Param("cid") Integer cid);
}
