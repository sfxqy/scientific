package cn.hsernos.dao;

import cn.hsernos.pojo.Team;
import cn.hsernos.pojo.TeamExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMapper {
    int countByExample(TeamExample example);

    int deleteByExample(TeamExample example);

    int deleteByPrimaryKey(Integer tid);

    int insert(Team record);

    int insertSelective(Team record);

    List<Team> selectByExample(TeamExample example);

    Team selectByPrimaryKey(Integer tid);

    int updateByExampleSelective(@Param("record") Team record, @Param("example") TeamExample example);

    int updateByExample(@Param("record") Team record, @Param("example") TeamExample example);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);


    //---------------------------------------------------------
    /*List<Team> getTeamServiceByName(@Param("cid")Integer cid,@Param("rid") Integer rid,@Param("name") String name);*/
    List<Team> getTeamByTeam(Team team);
}