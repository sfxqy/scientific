package cn.hsernos.dao;

import cn.hsernos.pojo.HserSkillUserVO;
import cn.hsernos.pojo.Skilluser;
import cn.hsernos.pojo.SkilluserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkilluserMapper {
    int countByExample(SkilluserExample example);

    int deleteByExample(SkilluserExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Skilluser record);

    int insertSelective(Skilluser record);

    List<Skilluser> selectByExample(SkilluserExample example);

    Skilluser selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Skilluser record, @Param("example") SkilluserExample example);

    int updateByExample(@Param("record") Skilluser record, @Param("example") SkilluserExample example);

    int updateByPrimaryKeySelective(Skilluser record);

    int updateByPrimaryKey(Skilluser record);


    List<HserSkillUserVO> getAllSkilluser(@Param("cid") Integer cid);

    List<HserSkillUserVO> getSkilluserByusername(Skilluser skilluser);

    Skilluser getSkilluserByUsername(@Param("username") String username);

    int addSkikkusers(@Param("uid") Integer uid);

}