package com.xingye.data.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xingye.data.content.common.utils.Query;
import com.xingye.data.content.entity.MetalPlateNumberQuery;
import com.xingye.data.content.entity.NumberTypeEntity;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xingye.data.content.common.utils.PageUtils;

import com.xingye.data.content.dao.NumberDataDao;
import com.xingye.data.content.entity.NumberDataEntity;
import com.xingye.data.content.service.NumberDataService;

import java.util.Map;
import java.util.Objects;


@Service("metalPlateNumberService")
public class NumberDataServiceImpl extends ServiceImpl<NumberDataDao, NumberDataEntity> implements NumberDataService {

    @Autowired
    private NumberDataDao numberDataDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<NumberDataEntity> queryWrapper = new QueryWrapper<>();
        if (!Objects.isNull(params.get("type"))) {
            queryWrapper.eq("type", params.get("type"));
        }

        IPage<NumberDataEntity> page = this.page(
                new Query<NumberDataEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public Integer queryMaxSerialNumber(NumberDataEntity numberDataEntity) {
        return numberDataDao.queryMaxSerialNumber(numberDataEntity);
    }

}