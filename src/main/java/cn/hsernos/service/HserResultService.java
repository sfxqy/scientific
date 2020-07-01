package cn.hsernos.service;

import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.common.utils.FileUtil;
import cn.hsernos.dao.*;
import cn.hsernos.pojo.Demand;
import cn.hsernos.pojo.Demandlist;
import cn.hsernos.pojo.Result;
import cn.hsernos.pojo.ResultExample;
import cn.hsernos.tools.Tool;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.jsqlparser.JSQLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class HserResultService {
    private static final Logger logger = LoggerFactory.getLogger(HserResultService.class);

    @Autowired
    private ResultMapper resultMapper;

    @Autowired
    private DemandlistMapper demandlistMapper;

    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private HserVoMapper voMapper;

    public void addResult(Result result, Integer id) {
        CheckUtil.mustNullOtherOpposite(result, "参数异常", "audit&uid&rid");
        CheckUtil.notNull(id, "参数异常");
        result.setUid(id);
        cn.hsernos.pojo.Service service = voMapper.findAuditService(result.getSid());
        if (service == null) {
            logger.info("[添加失败]");
            CheckUtil.fail("该资源编号不存在或未审核通过");
        } else if (!service.getUid().equals(id)) {
            logger.info("[添加失败]");
            CheckUtil.fail("该资源编号不属于自己");
        }
        Demand demand = getDemand(result.getDid());
        if (demand.getAudit() != 1) {
            logger.info("[添加失败]");
            CheckUtil.fail("该需求编号未审核通过");
        }
        if (demand == null) {
            logger.info("[添加失败]");
            CheckUtil.fail("该需求编号不存在");
        }
        logger.info("[添加对接成果中，参数[{},id:{}]]", result, id);
        resultMapper.insertSelective(result);
        logger.info("[添加成功]");
    }

    public void setResult(Result result, Integer id, HttpSession session) {
        CheckUtil.mustNull(result, "参数异常", "audit");
        CheckUtil.notNull(result, "参数异常", "rid");
        CheckUtil.notNull(id, "参数异常");
        Byte a = 0;
        result.setAudit(a);
        if (result.getSid() != null) {
            cn.hsernos.pojo.Service service = voMapper.findAuditService(result.getSid());
            if (service == null) {
                logger.info("[修改失败]");
                CheckUtil.fail("该资源编号不存在");
            } else if (!service.getUid().equals(id)) {
                logger.info("[修改失败]");
                CheckUtil.fail("该资源编号不属于自己");
            }
        }
        if (result.getDid() != null) {
            Demand demand = getDemand(result.getDid());
            if (demand == null) {
                logger.info("[修改失败]");
                CheckUtil.fail("该需求编号不存在");
            }
        }
        String delImg = null;
        if (result.getImg() != null) {
            String img = resultMapper.selectByPrimaryKey(result.getRid()).getImg();
            if (!img.equals(result.getImg())) {
                delImg = img;
            }
        }
        logger.info("[修改对接成果中，参数[{},id:{}]]", result, id);
        Result result1 = resultMapper.selectByPrimaryKey(result.getRid());
        FileUtil.deleteFile(session, delImg);
        resultMapper.updateByPrimaryKeySelective(result);
        logger.info("[修改成功]");
    }

    public Result getResult(Integer rid, Integer id) {
        CheckUtil.notNull(rid, "参数异常");
        CheckUtil.notNull(id, "参数异常");
        logger.info("[查询对接成果中，参数[{}]]");
        Result result = resultMapper.selectByPrimaryKey(rid);
        if (result == null || !result.getUid().equals(id)) {
            CheckUtil.fail("非法访问");
        }
        logger.info("[查找成功]");
        return result;
    }

    public Result getResult(Integer rid) {
        CheckUtil.notNull(rid, "参数异常");
        logger.info("[查询对接成果中，参数[rid={}]]", rid);
        ResultExample example = new ResultExample();
        example.or().andAuditGreaterThan((byte) 0).andRidEqualTo(rid);
        Result result = Tool.getListFirst(resultMapper.selectByExample(example));
        logger.info("[查找成功]");
        return result;
    }

    public void delResult(Integer rid, Integer id, HttpSession session) {
        CheckUtil.notNull(rid, "参数异常");
        CheckUtil.notNull(id, "参数异常");
        logger.info("[删除对接成果中，参数[{}]]");
        Result result = resultMapper.selectByPrimaryKey(rid);
        if (result == null || !result.getUid().equals(id)) {
            CheckUtil.fail("非法访问");
        } else {
            resultMapper.deleteByPrimaryKey(rid);
            FileUtil.deleteFile(session, result.getImg());
        }
        logger.info("[删除成功]");
    }


    public List<Result> getResultList(Integer uid) {
        CheckUtil.notNull(uid, "参数异常");
        ResultExample example = new ResultExample();
        example.or().andUidEqualTo(uid);
        List<Result> list = resultMapper.selectByExample(example);
        return list;
    }


    public PageInfo<Result> getResultList(Integer page, Integer aid) {
        CheckUtil.notNull(page, "参数异常");
        PageHelper.startPage(page, 10);
        ResultExample example = new ResultExample();
        if (aid != null) {
            example.or().andAidEqualTo(aid).andAuditGreaterThan((byte) 0);
        } else {
            example.or().andAuditGreaterThan((byte) 0);
        }
        PageInfo<Result> pageInfo = new PageInfo<Result>(resultMapper.selectByExample(example));
        return pageInfo;
    }

    public Demand getDemand(Integer id) {
        CheckUtil.notNull(id, "参数为空");
        logger.info("[查找中,参数[id:{}]]", id);
        Demandlist demandlist = demandlistMapper.selectByPrimaryKey(id);
        if (demandlist == null) {
            logger.info("[查找失败]");
            return null;
        }
        Demand demand = demandMapper.selectByPrimaryKey(demandlist.getDid());

        if (demand.getAudit() == 0) {
            logger.info("[查找失败]");
            return null;
        }
        logger.info("[查找成功]");
        return demand;
    }

}
