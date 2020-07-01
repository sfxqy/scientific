package cn.hsernos.service;

import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.dao.HserVoMapper;
import cn.hsernos.pojo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class HserVoService {
    private static final Logger logger = LoggerFactory.getLogger(HserVoService.class);

    @Autowired
    private HserVoMapper voMapper;

    @Autowired
    private HserListService listService;

    /**
     * 用户id查询某个用户的详细资料
     *
     * @param uid 用户id
     * @return 技术方详细信息
     */
    public HserSkillUserVO selectSkillUserById(Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[查询某个用户的详细资料，[uid:{}]]]", uid);
        HserSkillUserVO hserSkillUserVO = voMapper.selectSkillUserById(uid);
        logger.info("[查询某个用户的详细资料，用户信息是否完善{}", hserSkillUserVO == null);
        if (hserSkillUserVO == null) {
            hserSkillUserVO = voMapper.getskilluser(uid);
            logger.info("[第二次查询某个用户的详细资料，用户信息是否完善{}", hserSkillUserVO == null);
        }
        return hserSkillUserVO;
    }

    /**
     * 用户id查询某个用户所有发布的所有服务
     *
     * @param uid 用户id
     * @return 资料
     */
    public List<HserServiceVO> getServiceById(Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        List<HserServiceVO> vo=voMapper.getServiceById(uid);
        logger.info("[查询某个的所有服务，[uid:{}] [数据量：{}]]", uid,vo.size());
        return  vo;
    }

    /**
     * 用户id查询某个用户所有发布的需求
     *
     * @param uid 用户id
     * @return 自己所有发布的需求
     */
    public List<HserDemandVO> getDemandList(Integer uid) {
        CheckUtil.notNull(uid, "用户id为空");
        logger.info("[查询某个用户所有发布的需求，[uid:{}]]", uid);
        return voMapper.getDemandsByNid(uid);
    }

    /**
     * 分页查询所有技术人员信息
     *
     * @param page 第几页
     * @param info 模糊查找关键词，可以为空
     * @return
     */
    public PageInfo<HserSkillListVO> getSkillList(Integer page, String info) {
        CheckUtil.notNull(page, "页码为空");
        logger.info("[分页查询所有技术人员信息中,[page:{},info:{}]]", page, info);
        PageHelper.startPage(page, 2, "uid desc");
        PageInfo<HserSkillListVO> pageInfo = new PageInfo<HserSkillListVO>(voMapper.getSkillList(info));
        logger.info("[成功]");
        return pageInfo;
    }

    /**
     * 分页查询所有过审资源
     *
     * @param page 第几页
     * @param info 模糊查找关键词，可以为空
     * @return 部分过审资源
     */
    public PageInfo<HserServiceVO> findServiceList(Integer page, String info) {
        CheckUtil.notNull(page, "页码为空");
        logger.info("[分页查询所有过审资源中,[page:{},info:{}]]", page, info);
        PageHelper.startPage(page, 10, "sid desc");
        PageInfo<HserServiceVO> pageInfo = new PageInfo<HserServiceVO>(voMapper.findServiceList(info));
        logger.info("[成功]");
        return pageInfo;
    }

    /**
     * 根据主键查询对接成果
     *
     * @param rid 对接成果id
     * @return 一条对接成果记录
     */
    public HserResultVO findResultVO(Integer rid) {
        CheckUtil.notNull(rid, "参数为空");
        logger.info("[根据主键查询对接成果,参数[rid:{}]]", rid);
        HserResultVO hserResultVO = voMapper.findResultVo(rid);
        return hserResultVO;
    }

    /**
     * 查询统计数据
     *
     * @param cid  部门id，查询条件，可以为空
     * @param some 1：年:2：季:3：月
     * @return 统计数据
     */
    public List<HserStatisticsVO> getBySome(Integer cid, Integer some) {
        CheckUtil.notNull(some, "参数为空");
        logger.info("[获取统计数据，参数[cid:{},Some:{}]]", cid, some);
        switch (some) {
            case 1:
                return voMapper.getByYear(cid);
            case 2:
                return voMapper.getByQuarter(cid);
            case 3:
                return voMapper.getByMonth(cid);
            default:
                return voMapper.getByYear(cid);
        }
    }


    public List<String> getYears() {
        ArrayList<String> list = new ArrayList<>();
        List<HserStatisticsVO> lists = getBySome(null, 1);
        for (HserStatisticsVO vo : lists) {
            list.add(vo.getTimes());
        }
        return list;
    }


    public List<HserStatisticsVO> getGroupByRid(String times) {
        List<Resource> list = listService.getResources();
        List<HserStatisticsVO> vos = new ArrayList<HserStatisticsVO>();
        for (Resource resource : list) {
            HserStatisticsVO vo = voMapper.getByRid(resource.getRid(), times);
            if (vo != null) {
                vo.setTimes(resource.getResource());
            } else {
                vo = new HserStatisticsVO();
                vo.setTimes(resource.getResource());
                vo.setTurnover(0);
                vo.setBenefits((double) 0);
                vo.setSum(0);
            }
            vos.add(vo);
        }
        return vos;
    }


    public List<HserStatisticsVO> getYearsInfo() {
        ArrayList<HserStatisticsVO> list = new ArrayList<HserStatisticsVO>();
        List<HserStatisticsVO> lists = getBySome(null, 1);
        if (lists != null) {
            int k = Integer.parseInt(lists.get(0).getTimes());
            int i = 0;
            while (i < lists.size()) {
                if (lists.get(i).getTimes().equals(String.valueOf(k))) {
                    list.add(lists.get(i));
                    k++;
                    i++;
                } else {
                    HserStatisticsVO vo = new HserStatisticsVO();
                    vo.setTimes(String.valueOf(k));
                    vo.setBenefits((double) 0);
                    vo.setSum(0);
                    vo.setTurnover(0);
                    list.add(vo);
                    k++;
                }
            }
        }
        return list;
    }

    public List<HserStatisticsVO> getMonthInfoByYear(String year) {
        if (year.length() != 4) {
            CheckUtil.fail("参数不是年份");
        }
        ArrayList<HserStatisticsVO> list = new ArrayList<HserStatisticsVO>();
        List<HserStatisticsVO> lists = getBySome(null, 3);
        Iterator<HserStatisticsVO> it = lists.iterator();
        while (it.hasNext()) {
            HserStatisticsVO vo = it.next();
            if (!vo.getTimes().startsWith(year)) {
                it.remove();
            }
        }
        if (lists.size() != 0) {
            int k = Integer.parseInt(year + "01");
            int i = 0;
            while (i < lists.size()) {
                if (lists.get(i).getTimes().equals(String.valueOf(k))) {
                    list.add(lists.get(i));
                    k++;
                    i++;
                } else {
                    HserStatisticsVO vo = new HserStatisticsVO();
                    vo.setTimes(String.valueOf(k));
                    vo.setBenefits((double) 0);
                    vo.setSum(0);
                    vo.setTurnover(0);
                    list.add(vo);
                    k++;
                }
            }
            while (k < Integer.parseInt(year + "13")) {
                HserStatisticsVO vo = new HserStatisticsVO();
                vo.setTimes(String.valueOf(k));
                vo.setBenefits((double) 0);
                vo.setSum(0);
                vo.setTurnover(0);
                list.add(vo);
                k++;
            }
        }
        return list;
    }
}
