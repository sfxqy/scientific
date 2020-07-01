package cn.hsernos.dao;

import cn.hsernos.pojo.Software;
import cn.hsernos.pojo.SoftwareExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareMapper {
    int countByExample(SoftwareExample example);

    int deleteByExample(SoftwareExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Software record);

    int insertSelective(Software record);

    List<Software> selectByExample(SoftwareExample example);

    Software selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Software record, @Param("example") SoftwareExample example);

    int updateByExample(@Param("record") Software record, @Param("example") SoftwareExample example);

    int updateByPrimaryKeySelective(Software record);

    int updateByPrimaryKey(Software record);

    //---------------------------------
    /* List<Software> getSoftwareServiceByName(@Param("cid")Integer cid,@Param("rid") Integer rid,@Param("name") String name);*/
    List<Software> getSoftwareBySoftware(Software software);
}