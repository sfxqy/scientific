package cn.hsernos.dao;

import cn.hsernos.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface HserVoMapper {

    /**
     * @param info 搜索条件
     * @return 已审核需求列表
     */
    List<HserDemandVO> getDemands(@Param("info") String info);

    /**
     * @param nid 用户id
     * @return 需求列表
     */
    List<HserDemandVO> getDemandsByNid(Integer nid);

    /**
     * @param sid 服务id
     * @return 已审核的服务
     */
    Service findAuditService(Integer sid);

    /**
     * @param uid 技术方id
     * @return 技术方信息
     */
    HserSkillUserVO selectSkillUserById(Integer uid);

    /**
     * @param uid 用户id
     * @return 服务列表
     */
    List<HserServiceVO> getServiceById(Integer uid);


    /**
     * @param info 搜索条件
     * @return 技术人员列表
     */
    List<HserSkillListVO> getSkillList(@Param("info") String info);

    /**
     * @param info 搜索条件
     * @return 已审核服务列表
     */
    List<HserServiceVO> findServiceList(@Param("info") String info);

    /**
     * @param rid 成果id
     * @return 对接成果
     */
    HserResultVO findResultVo(Integer rid);

    /**
     * @param cid 部门，可为空。
     * @return 按年的统计结果
     */
    List<HserStatisticsVO> getByYear(@Param("cid") Integer cid);

    /**
     * @param cid 部门，可为空。
     * @return 按季的统计结果
     */
    List<HserStatisticsVO> getByQuarter(@Param("cid") Integer cid);

    /**
     * @param cid 部门，可为空。
     * @return 按月的统计结果
     */
    List<HserStatisticsVO> getByMonth(@Param("cid") Integer cid);

    /**
     * @param rid   资源类别
     * @param times 年份
     * @return 按年按资源类别的统计结果
     */
    HserStatisticsVO getByRid(@Param("rid") Integer rid, @Param("times") String times);

    HserSkillUserVO getskilluser(@Param("uid") Integer uid);


}
