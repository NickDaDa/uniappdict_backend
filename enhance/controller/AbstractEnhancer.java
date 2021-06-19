/**
 * 
 */
package com.jeecg.devicemanage.wx.enhance.controller;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jeecg.devicemanage.wx.enhance.anno.UniAppDict;

/**
 * template pattern
 * 
 * @author renqing
 *
 * Jun 16, 2021
 */
public abstract class AbstractEnhancer<T> {

	/**
	 * result set
	 */
	protected List<T> result;
	
	/**
	 * public Sub() {
	 *   super(request)
	 * }
	 * 
	 */
	protected HttpServletRequest request;
	
	/**
	 * declare data getter
	 * @param request
	 * @return
	 */
	protected abstract List<T> selectDataSet(HttpServletRequest request);
	
	/**
	 * entrance
	 * call this method in controller
	 * @return
	 */
	public String noNoodles() {
		
		// initial data set
		result = selectDataSet(request);
		
		// resultSet objects outside looper
		for (T t : result) {
			
			Field[] fields = t.getClass().getDeclaredFields();
			
			// fields looper
			for (Field f : fields) {
				
				UniAppDict an = f.getAnnotation(UniAppDict.class); 
				if (null != an) {
					
					// call dictionary service
					// do data wrap business
					
					
				}
			}
			
		}
		return "";
	}
}
