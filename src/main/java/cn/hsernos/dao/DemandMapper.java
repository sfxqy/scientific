package cn.hsernos.dao;

import cn.hsernos.pojo.Demand;
import cn.hsernos.pojo.DemandExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandMapper {
    int countByExample(DemandExample example);

    int deleteByExample(DemandExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(Demand record);

    int insertSelective(Demand record);

    List<Demand> selectByExample(DemandExample example);

    Demand selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") Demand record, @Param("example") DemandExample example);

    int updateByExample(@Param("record") Demand record, @Param("example") DemandExample example);

    int updateByPrimaryKeySelective(Demand record);

    int updateByPrimaryKey(Demand record);
}