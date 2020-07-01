package cn.hsernos.dao;

import cn.hsernos.pojo.Result;
import cn.hsernos.pojo.ResultExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultMapper {
    int countByExample(ResultExample example);

    int deleteByExample(ResultExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(Result record);

    int insertSelective(Result record);

    List<Result> selectByExample(ResultExample example);

    Result selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") Result record, @Param("example") ResultExample example);

    int updateByExample(@Param("record") Result record, @Param("example") ResultExample example);

    int updateByPrimaryKeySelective(Result record);

    int updateByPrimaryKey(Result record);


    Result selectBySid(@Param("sid") Integer sid);

    Result selectByDid(@Param("did") Integer did);

    List<Result> selectByAid(@Param("aid") Integer aid);

    List<Result> selectByAidAndCid(@Param("aid") Integer aid, @Param("cid") Integer cid);


    //根据pid获取Result
    List<Result> getResultByPid(@Param("ids") Integer pid, @Param("rid") Integer rid);

    List<Result> selectAllResultByCid(@Param("cid") Integer cid);

    List<Result> selectAllResultByCidAndAid(@Param("cid") Integer cid, @Param("aid") Integer aid);

    List<Result> getResultByDid(@Param("did") Integer did);


    List<Result> selectByCid(@Param("cid") Integer cid);


}