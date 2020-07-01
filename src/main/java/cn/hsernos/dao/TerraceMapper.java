package cn.hsernos.dao;

import cn.hsernos.pojo.Terrace;
import cn.hsernos.pojo.TerraceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TerraceMapper {
    int countByExample(TerraceExample example);

    int deleteByExample(TerraceExample example);

    int deleteByPrimaryKey(Integer tid);

    int insert(Terrace record);

    int insertSelective(Terrace record);

    List<Terrace> selectByExample(TerraceExample example);

    Terrace selectByPrimaryKey(Integer tid);

    int updateByExampleSelective(@Param("record") Terrace record, @Param("example") TerraceExample example);

    int updateByExample(@Param("record") Terrace record, @Param("example") TerraceExample example);

    int updateByPrimaryKeySelective(Terrace record);

    int updateByPrimaryKey(Terrace record);


    //------------------------------------------------
    /* List<Terrace> getTerraceServiceByName(@Param("cid")Integer cid,@Param("rid") Integer rid,@Param("name") String name);*/
    List<Terrace> getTerraceByTerrace(Terrace terrace);
}