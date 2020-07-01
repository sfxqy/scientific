package cn.hsernos.dao;

import cn.hsernos.pojo.Demandlist;
import cn.hsernos.pojo.DemandlistExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandlistMapper {
    int countByExample(DemandlistExample example);

    int deleteByExample(DemandlistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Demandlist record);

    int insertSelective(Demandlist record);

    List<Demandlist> selectByExample(DemandlistExample example);

    Demandlist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Demandlist record, @Param("example") DemandlistExample example);

    int updateByExample(@Param("record") Demandlist record, @Param("example") DemandlistExample example);

    int updateByPrimaryKeySelective(Demandlist record);

    int updateByPrimaryKey(Demandlist record);

    int deteByDid(@Param("did") Integer did);

    List<Demandlist> selectByNid(@Param("nid") Integer nid);

}