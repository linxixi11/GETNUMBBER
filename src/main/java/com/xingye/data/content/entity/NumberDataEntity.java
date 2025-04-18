package com.xingye.data.content.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 
 * 
 * @author sss
 * @email sss@email.xingyedata.com
 * @date 2025-04-07 15:18:13
 */
@Data
@TableName("number_data")
public class NumberDataEntity implements Serializable {
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
	private String serialNumber;

	private Long type;
	/**
	 * 
	 */
	private String name;

	
//	private String imageBase64;
	/**
	 * 
	 */
	private Integer router;

//	private String material;
//	private String thickness;
//	private String qty;
//	private String boundingBoxLength;
//	private String boundingBoxWidth;
//	private String remark;
	/**
	 *
	 */
	@TableField(fill = FieldFill.INSERT)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime created;
	/**
	 *
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE) // 必须添加此注解
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime  lastUpdated;

}
