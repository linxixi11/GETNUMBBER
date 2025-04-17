package com.xingye.data.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xingye.data.content.common.utils.Query;
import com.xingye.data.content.entity.MetalPlateNumberQuery;
import com.xingye.data.content.entity.NumberTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingye.data.content.common.utils.PageUtils;

import com.xingye.data.content.dao.NumberDataDao;
import com.xingye.data.content.entity.NumberDataEntity;
import com.xingye.data.content.service.NumberDataService;

import java.util.Map;


@Service("metalPlateNumberService")
public class NumberDataServiceImpl extends ServiceImpl<NumberDataDao, NumberDataEntity> implements NumberDataService {

    @Autowired
    private NumberDataDao numberDataDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<NumberDataEntity> page = this.page(
                new Query<NumberDataEntity>().getPage(params),
                new QueryWrapper<NumberDataEntity>().eq("type",params.get("type"))
        );
        return new PageUtils(page);
    }

    @Override
    public Integer queryMaxSerialNumber(NumberDataEntity numberDataEntity) {
        return numberDataDao.queryMaxSerialNumber(numberDataEntity);
    }

}