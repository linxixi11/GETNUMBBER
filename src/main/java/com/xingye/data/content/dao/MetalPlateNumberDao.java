package com.xingye.data.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xingye.data.content.entity.MetalPlateNumberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
@Mapper
public interface MetalPlateNumberDao extends BaseMapper<MetalPlateNumberEntity> {

    String queryMaxSerialNumber(MetalPlateNumberEntity entity);

    IPage<MetalPlateNumberEntity> selectPageByTypeId(IPage<MetalPlateNumberEntity> page,@Param("typeId") Long typeId);
}
