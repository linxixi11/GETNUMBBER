package com.xingye.data.content.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.xingye.data.content.entity.MetalPlateNumberQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xingye.data.content.entity.NumberDataEntity;
import com.xingye.data.content.service.NumberDataService;
import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.R;



/**
 * 
 *
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
@RestController
@RequestMapping("/number/data")
public class NumberDataController {
    @Autowired
    private NumberDataService numberDataService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestBody Map<String, Object> params){
        PageUtils page = numberDataService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		NumberDataEntity metalPlateNumber = numberDataService.getById(id);

        return R.ok().put("metalPlateNumber", metalPlateNumber);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody NumberDataEntity metalPlateNumber){
		numberDataService.save(metalPlateNumber);
        return R.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/max")
    public R max(@RequestBody NumberDataEntity metalPlateNumber){
        return R.ok().put("data", numberDataService.queryMaxSerialNumber(metalPlateNumber));
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody NumberDataEntity metalPlateNumber){
		numberDataService.updateById(metalPlateNumber);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody NumberDataEntity metalPlateNumbers){
		numberDataService.removeById(metalPlateNumbers);
        return R.ok();
    }

}
