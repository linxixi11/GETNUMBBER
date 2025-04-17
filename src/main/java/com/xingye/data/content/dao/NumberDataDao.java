package com.xingye.data.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xingye.data.content.entity.NumberDataEntity;
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
public interface NumberDataDao extends BaseMapper<NumberDataEntity> {

    Integer queryMaxSerialNumber(NumberDataEntity entity);

    IPage<NumberDataEntity> selectPageByTypeId(IPage<NumberDataEntity> page, @Param("typeId") Long typeId);
}
