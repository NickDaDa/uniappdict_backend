/**
 * 
 */
package com.jeecg.devicemanage.wx.enhance.service;

import java.util.List;

import com.jeecg.devicemanage.wx.enhance.anno.UniAppDict;
import com.jeecg.devicemanage.wx.enhance.vo.DictVo;

/**
 * wechat data select enhancer
 * @author renqing
 *
 * Jun 16, 2021
 */
public interface EnhancerService {
	
	
	// ===================================== Dict Enhancer Begin =====================================
	/**
	 * simplex code -> text, one on one
	 * @param code
	 * @return text value
	 */
	String simpleSelect(String dictGroupCode, String codeVal);
	
	/**
	 * multiplex select-option 
	 * @param code
	 * @return json string
	 * {
	 *   "data": "{
	 *     "": "",
	 *     "": "",
	 *     ...
	 *     "": ""
	 *   }", // whole group
	 *   "current: "" // current selected value
	 * }
	 */
	List<DictVo> selectionSel(String dictGroupCode);
	
	/**
	 * select with special table
	 * @param field
	 * @return
	 */
	String specialSel(UniAppDict anno);
	
	// ===================================== Dict Enhancer End =====================================
	
	/**
	 * test customized service Example
	 * @param str
	 * @return
	 */
	String useService(List<String> str);
	
	
}
