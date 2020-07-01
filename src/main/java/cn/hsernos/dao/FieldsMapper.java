package cn.hsernos.dao;

import cn.hsernos.pojo.Fields;
import cn.hsernos.pojo.FieldsExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldsMapper {
    int countByExample(FieldsExample example);

    int deleteByExample(FieldsExample example);

    int deleteByPrimaryKey(Integer fid);

    int insert(Fields record);

    int insertSelective(Fields record);

    List<Fields> selectByExample(FieldsExample example);

    Fields selectByPrimaryKey(Integer fid);

    int updateByExampleSelective(@Param("record") Fields record, @Param("example") FieldsExample example);

    int updateByExample(@Param("record") Fields record, @Param("example") FieldsExample example);

    int updateByPrimaryKeySelective(Fields record);

    int updateByPrimaryKey(Fields record);
}