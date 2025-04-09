package com.xingye.data.content.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.xingye.data.content.entity.MetalPlateNumberQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xingye.data.content.entity.MetalPlateNumberEntity;
import com.xingye.data.content.service.MetalPlateNumberService;
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
@RequestMapping("/metalPlateNumber")
public class MetalPlateNumberController {
    @Autowired
    private MetalPlateNumberService metalPlateNumberService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody MetalPlateNumberQuery query){
        PageUtils page = metalPlateNumberService.queryPage(query);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		MetalPlateNumberEntity metalPlateNumber = metalPlateNumberService.getById(id);

        return R.ok().put("metalPlateNumber", metalPlateNumber);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MetalPlateNumberEntity metalPlateNumber){
        metalPlateNumber.setCreated(new Date());
        metalPlateNumber.setLastUpdated(new Date());
		metalPlateNumberService.save(metalPlateNumber);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/max")
    public R max(@RequestBody MetalPlateNumberEntity metalPlateNumber){
        String serialNumber = metalPlateNumberService.queryMaxSerialNumber(metalPlateNumber);
        return R.ok().put("data", serialNumber);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MetalPlateNumberEntity metalPlateNumber){
		metalPlateNumberService.updateById(metalPlateNumber);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		metalPlateNumberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
