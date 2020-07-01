package cn.hsernos.service;

import cn.hsernos.common.exceptions.NoPermissionException;
import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.common.utils.FileUtil;
import cn.hsernos.dao.DemandMapper;
import cn.hsernos.dao.DemandlistMapper;
import cn.hsernos.dao.HserVoMapper;
import cn.hsernos.pojo.Demand;
import cn.hsernos.pojo.Demandlist;
import cn.hsernos.pojo.DemandlistExample;
import cn.hsernos.pojo.HserDemandVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Service
public class HserDemandService {

    private static final Logger logger = LoggerFactory.getLogger(HserDemandService.class);

    @Autowired
    private DemandMapper demandMapperd;

    @Autowired
    private DemandlistMapper demandlistMapper;

    @Autowired
    private HserVoMapper voMapper;

    public Integer getNidByDid(Integer did) {
        CheckUtil.notNull(did, "参数异常");
        DemandlistExample example = new DemandlistExample();
        example.or().andDidEqualTo(did);
        List<Demandlist> list = demandlistMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0).getNid();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addDemand(Demand demand, Integer nid) throws IOException {
        CheckUtil.mustNullOtherOpposite(demand, "添加异常", "audit&did");
        CheckUtil.notNull(nid, "添加异常");
        logger.info("[开始发布需求,参数[{}]]", demand);
        demandMapperd.insertSelective(demand);
        Demandlist demandlist = new Demandlist();
        demandlist.setDid(demand.getDid());
        demandlist.setNid(nid);
        demandlistMapper.insertSelective(demandlist);
        logger.info("[发布成功]");
    }

    public Demand getDemand(Integer did, Integer nid) {
        if (nid.equals(getNidByDid(did))) {
            return getDemand(did);
        } else {
            throw new NoPermissionException("没有权限");
        }
    }

    public Demand getDemand(Integer did) {
        logger.info("[查找需求中,参数[did:{}]]", did);
        Demand demand = demandMapperd.selectByPrimaryKey(did);
        logger.info("[查找成功]");
        return demand;
    }

    public Demand getDemandLimitAudit(Integer did) {
        logger.info("[查找需求中,参数[did:{}]]", did);
        Demand demand = demandMapperd.selectByPrimaryKey(did);
        if (demand.getAudit() <= 0) {
            return null;
        }
        logger.info("[查找成功]");
        return demand;
    }


    public void setDemand(Demand demand, HttpSession session, Integer nid) throws IOException {
        CheckUtil.mustNull(demand, "修改异常", "audit");
        CheckUtil.notNull(nid, "修改异常");

        Demand demand1 = getDemand(demand.getDid());
        if (demand1 == null || !nid.equals(getNidByDid(demand.getDid()))) {
            throw new NoPermissionException("没有权限");
        }
        demand.setAudit((byte) 0);
        String delImg = null;
        if (demand.getImg() != null) {
            String img = demandMapperd.selectByPrimaryKey(demand.getDid()).getImg();
            if (!img.equals(demand.getImg())) {
                delImg = img;
            }
        }
        logger.info("[开始修改需求,参数[{}]]", demand);
        demandMapperd.updateByPrimaryKeySelective(demand);
        FileUtil.deleteFile(session, delImg);
        logger.info("[修改成功]");
    }

    @Transactional(rollbackFor = Exception.class)
    public void delDemand(Integer did, Integer nid, HttpSession session) {
        CheckUtil.notNull(did, "参数为空");
        CheckUtil.notNull(nid, "参数为空");
        Demand demand = getDemand(did);
        if (demand == null || !nid.equals(getNidByDid(did))) {
            throw new NoPermissionException("没有权限");
        }
        logger.info("[删除中,参数[did:{}]]", did);
        demandMapperd.deleteByPrimaryKey(did);
        DemandlistExample example = new DemandlistExample();
        example.or().andDidEqualTo(did);
        demandlistMapper.deleteByExample(example);
        FileUtil.deleteFile(session, demand.getImg());
        logger.info("[删除成功]");
    }


    public PageInfo<HserDemandVO> getDemands(Integer page, String info) {
        CheckUtil.notNull(page, "页码为空");
        PageHelper.startPage(page, 10, "did desc");
        PageInfo<HserDemandVO> pageInfo = new PageInfo<HserDemandVO>(voMapper.getDemands(info));
        return pageInfo;
    }

    public List<HserDemandVO> getDemandsByNid(Integer nid) {
        List<HserDemandVO> list = voMapper.getDemandsByNid(nid);
        return list;
    }
}
