package cn.hsernos.dao;

import cn.hsernos.pojo.Skillusers;
import cn.hsernos.pojo.SkillusersExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillusersMapper {
    int countByExample(SkillusersExample example);

    int deleteByExample(SkillusersExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Skillusers record);

    int insertSelective(Skillusers record);

    List<Skillusers> selectByExample(SkillusersExample example);

    Skillusers selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Skillusers record, @Param("example") SkillusersExample example);

    int updateByExample(@Param("record") Skillusers record, @Param("example") SkillusersExample example);

    int updateByPrimaryKeySelective(Skillusers record);

    int updateByPrimaryKey(Skillusers record);


    int addSkillusers(Skillusers record);

}