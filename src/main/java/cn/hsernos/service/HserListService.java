package cn.hsernos.service;

import cn.hsernos.common.utils.CheckUtil;
import cn.hsernos.dao.*;
import cn.hsernos.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class HserListService {

    private static final Logger logger = LoggerFactory.getLogger(HserListService.class);

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private FieldsMapper fieldsMapper;

    @Autowired
    private CollegeMapper collegeMapper;

    @Autowired
    private AchievementMapper achievementMapper;

    @Autowired
    private ResourceMapper resourceMapper;


    public List<College> getColleges() {
        logger.info("[查询所有部门/学院]");
        List<College> list = collegeMapper.selectByExample(null);
        logger.info("[查询成功]");
        return list;
    }


    public List<Achievement> getAchievements() {
        logger.info("[查询所有行动]");
        List<Achievement> list = achievementMapper.selectByExample(null);
        logger.info("[查询成功]");
        return list;
    }

    public List<Field> getFieldList() {
        logger.info("[查询所有的一级专业领域]");
        List<Field> list = fieldMapper.selectByExample(null);
        logger.info("[查询成功]");
        return list;
    }

    public List<Fields> getFieldsListById(Integer id) {
        CheckUtil.notNull(id, "参数不为空");
        logger.info("[根据一级专业领域id：<{}>查询对应的二级专业领域]", id);
        FieldsExample fieldsExample = new FieldsExample();
        fieldsExample.or().andIdEqualTo(id);
        List<Fields> list = fieldsMapper.selectByExample(fieldsExample);
        logger.info("[查询成功]");
        return list;
    }


    public List<Resource> getResources() {
        logger.info("[查询所有资源类别]");
        List<Resource> list = resourceMapper.selectByExample(null);
        logger.info("[查询成功]");
        return list;
    }

}
