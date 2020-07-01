package cn.hsernos.dao;

import cn.hsernos.pojo.Patent;
import cn.hsernos.pojo.PatentExample;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentMapper {
    int countByExample(PatentExample example);

    int deleteByExample(PatentExample example);

    int deleteByPrimaryKey(Integer pid);

    int insert(Patent record);

    int insertSelective(Patent record);

    List<Patent> selectByExample(PatentExample example);

    Patent selectByPrimaryKey(Integer pid);

    int updateByExampleSelective(@Param("record") Patent record, @Param("example") PatentExample example);

    int updateByExample(@Param("record") Patent record, @Param("example") PatentExample example);

    int updateByPrimaryKeySelective(Patent record);

    int updateByPrimaryKey(Patent record);

    /* List<Patent> getPatentServiceByName(@Param("cid") Byte cid, @Param("name") String name, @Param("rid") Integer rid);*/
    List<Patent> getPatenByPaten(Patent patent);
}