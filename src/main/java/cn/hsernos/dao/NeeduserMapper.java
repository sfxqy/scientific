package cn.hsernos.dao;

import cn.hsernos.pojo.Needuser;
import cn.hsernos.pojo.NeeduserExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NeeduserMapper {
    int countByExample(NeeduserExample example);

    int deleteByExample(NeeduserExample example);

    int deleteByPrimaryKey(Integer nid);

    int insert(Needuser record);

    int insertSelective(Needuser record);

    List<Needuser> selectByExample(NeeduserExample example);

    Needuser selectByPrimaryKey(Integer nid);

    int updateByExampleSelective(@Param("record") Needuser record, @Param("example") NeeduserExample example);

    int updateByExample(@Param("record") Needuser record, @Param("example") NeeduserExample example);

    int updateByPrimaryKeySelective(Needuser record);

    int updateByPrimaryKey(Needuser record);


    Needuser getNeeduserByDid(@Param("did") Integer did);
}