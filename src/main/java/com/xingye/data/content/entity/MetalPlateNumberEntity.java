package com.xingye.data.content.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
@Data
@TableName("metal_plate_number")
public class MetalPlateNumberEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private String corp;
	/**
	 * 
	 */
	private Long serialNumber;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Integer roules;
	/**
	 * 
	 */
	private Date created;
	/**
	 * 
	 */
	private Date lastUpdated;

}
