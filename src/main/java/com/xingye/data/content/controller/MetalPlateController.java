package com.xingye.data.content.controller;

import com.xingye.data.content.common.utils.PageUtils;
import com.xingye.data.content.common.utils.R;
import com.xingye.data.content.entity.MetalPlateEntity;
import com.xingye.data.content.service.MetalPlateService;
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
@RequestMapping("/metalPlate")
public class MetalPlateController {
    @Autowired
    private MetalPlateService metalPlateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = metalPlateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MetalPlateEntity metalPlate = metalPlateService.getById(id);

        return R.ok().put("metalPlate", metalPlate);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MetalPlateEntity metalPlate){
		metalPlateService.save(metalPlate);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MetalPlateEntity metalPlate){
		metalPlateService.updateById(metalPlate);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		metalPlateService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
