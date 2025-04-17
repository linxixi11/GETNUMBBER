package com.xingye.data.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.entity.NumberTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
public interface NumberTypeService extends IService<NumberTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

