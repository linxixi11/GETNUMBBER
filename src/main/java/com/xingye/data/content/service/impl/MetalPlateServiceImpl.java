package com.xingye.data.content.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.Query;

import com.xingye.data.content.dao.MetalPlateDao;
import com.xingye.data.content.entity.MetalPlateEntity;
import com.xingye.data.content.service.MetalPlateService;


@Service("metalPlateService")
public class MetalPlateServiceImpl extends ServiceImpl<MetalPlateDao, MetalPlateEntity> implements MetalPlateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MetalPlateEntity> page = this.page(
                new Query<MetalPlateEntity>().getPage(params),
                new QueryWrapper<MetalPlateEntity>()
        );

        return new PageUtils(page);
    }

}