package cn.hsernos.dao;

import cn.hsernos.pojo.Achievement;
import cn.hsernos.pojo.AchievementExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementMapper {
    int countByExample(AchievementExample example);

    int deleteByExample(AchievementExample example);

    int deleteByPrimaryKey(Integer aid);

    int insert(Achievement record);

    int insertSelective(Achievement record);

    List<Achievement> selectByExample(AchievementExample example);

    Achievement selectByPrimaryKey(Integer aid);

    int updateByExampleSelective(@Param("record") Achievement record, @Param("example") AchievementExample example);

    int updateByExample(@Param("record") Achievement record, @Param("example") AchievementExample example);

    int updateByPrimaryKeySelective(Achievement record);

    int updateByPrimaryKey(Achievement record);

    //author:sfx-------------------------------------------------
    int addAchievement(Achievement achievement);

    Achievement getAchievementByachievement(@Param("achievement") String achievement);
}