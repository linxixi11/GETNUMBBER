package com.xingye.data.content.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.Query;

import com.xingye.data.content.dao.NumberTypeDao;
import com.xingye.data.content.entity.NumberTypeEntity;
import com.xingye.data.content.service.NumberTypeService;


@Service("metalPlateService")
public class NumberTypeServiceImpl extends ServiceImpl<NumberTypeDao, NumberTypeEntity> implements NumberTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<NumberTypeEntity> page = this.page(
                new Query<NumberTypeEntity>().getPage(params),
                new QueryWrapper<NumberTypeEntity>()
        );

        return new PageUtils(page);
    }

}