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
	/**
	 * 
	 */
	private Integer router;
	/**
	 * 
	 */
	private Date created;
	/**
	 * 
	 */
	private Date lastUpdated;

}
