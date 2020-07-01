package cn.hsernos.dao;

import cn.hsernos.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer uid);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);



    Admin login(@Param("username") String username, @Param("password") String password);


    List<HserSkillUserVO> getskillUserInfo(@Param("username") String username, @Param("name") String name);

    //查询专利资源信息
    List<Patent> getAllPatent(@Param("cid") Integer cid);

    //查询平台资源
    List<Terrace> getAllTerrace(@Param("cid") Integer cid);


    //查询团队信息
    List<Team> getAllTeam(@Param("cid") Integer cid);

    //软件著作权信息
    List<Software> getAllSoftware(@Param("cid") Integer cid);


    //校企联合资源
    List<School> getAllSchool(@Param("cid") Integer cid);


    //查询所有需求信息
    List<Demand> getAllDemand();

    //查询所有需求方信息
    List<Needuser> getAllNeeduser();

    //获取成果分类信息achievement
    List<Achievement> getAllAchievement();

    //查询所有管理员
    List<Admin> getAllAdmin();

    //重置管理员密码
    int resettingPassword(@Param("uid") Integer uid, @Param("password") String password);

    //删除管理员
    int deleteAdmin(@Param("uid") Integer uid);

    //添加管理员信息
    int addAdmin(@Param("username") String username, @Param("password") String password, @Param("power") Byte power, @Param("cid") Integer cid);


    int resettingSkilluserPassword(@Param("username") String username, @Param("password") String password);

    Admin getAdminByUser(@Param("username") String username);

    HserSkillUserVO getInfoByRidAndIds(@Param("rid") Integer rid, @Param("ids") Integer ids);
}