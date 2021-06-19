/**
 * 
 */
package com.jeecg.devicemanage.wx.enhance.vo;

import java.io.Serializable;

/**
 * @author renqing
 *
 * Jun 18, 2021
 */
public class DictVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3279377918276335084L;

	/**
	 * 
	 */
	private String code;
	
	/**
	 * 
	 */
	private String text;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
