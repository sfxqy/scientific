package cn.hsernos.dao;

import cn.hsernos.pojo.Service;
import cn.hsernos.pojo.ServiceExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceMapper {
    int countByExample(ServiceExample example);

    int deleteByExample(ServiceExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Service record);

    int insertSelective(Service record);

    List<Service> selectByExample(ServiceExample example);

    Service selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Service record, @Param("example") ServiceExample example);

    int updateByExample(@Param("record") Service record, @Param("example") ServiceExample example);

    int updateByPrimaryKeySelective(Service record);

    int updateByPrimaryKey(Service record);


    Service selectByIds(@Param("ids") Integer ids);

    int deleteByIds(@Param("ids") Integer pid);

    int deleteByIdsAndRid(@Param("ids") Integer ids, @Param("rid") Integer rid);

    List<Service> getServiceByUid(@Param("uid") Integer uid);

}