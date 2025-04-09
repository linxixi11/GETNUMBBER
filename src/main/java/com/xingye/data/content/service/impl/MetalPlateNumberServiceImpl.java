package com.xingye.data.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xingye.data.content.entity.MetalPlateNumberQuery;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MetalPlateNumberDao metalPlateNumberDao;

    @Override
    public PageUtils queryPage(MetalPlateNumberQuery query) {
        if (query.getPage() == null || query.getPage() < 1) {
            query.setPage(1L);
        }
        Page<MetalPlateNumberEntity> page = new Page(query.getPage(),query.getPageSize());
        return new PageUtils(metalPlateNumberDao.selectPageByTypeId(page,query.getTypeId()));
    }

    @Override
    public String queryMaxSerialNumber(MetalPlateNumberEntity metalPlateNumberEntity) {
        return metalPlateNumberDao.queryMaxSerialNumber(metalPlateNumberEntity);
    }

}