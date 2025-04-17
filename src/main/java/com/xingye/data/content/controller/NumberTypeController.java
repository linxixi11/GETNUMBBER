package com.xingye.data.content.controller;

import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.R;
import com.xingye.data.content.entity.NumberTypeEntity;
import com.xingye.data.content.service.NumberTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
@RestController
@RequestMapping("/number/type")
public class NumberTypeController {
    @Autowired
    private NumberTypeService numberTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = numberTypeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		NumberTypeEntity metalPlate = numberTypeService.getById(id);

        return R.ok().put("metalPlate", metalPlate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody NumberTypeEntity metalPlate){
		numberTypeService.save(metalPlate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody NumberTypeEntity metalPlate){
		numberTypeService.updateById(metalPlate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody NumberTypeEntity numberType){
		numberTypeService.removeById(numberType);
        return R.ok();
    }

}
