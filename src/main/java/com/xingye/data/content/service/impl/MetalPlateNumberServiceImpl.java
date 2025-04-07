package com.xingye.data.content.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.Query;

import com.xingye.data.content.dao.MetalPlateNumberDao;
import com.xingye.data.content.entity.MetalPlateNumberEntity;
import com.xingye.data.content.service.MetalPlateNumberService;


@Service("metalPlateNumberService")
public class MetalPlateNumberServiceImpl extends ServiceImpl<MetalPlateNumberDao, MetalPlateNumberEntity> implements MetalPlateNumberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MetalPlateNumberEntity> page = this.page(
                new Query<MetalPlateNumberEntity>().getPage(params),
                new QueryWrapper<MetalPlateNumberEntity>()
        );

        return new PageUtils(page);
    }

}