package com.jeecg.devicemanage.wx.enhance.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * annotated which need to be makeup
 * @author renqing
 *
 * Jun 16, 2021
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniAppDict {
	
	/**
	 * mapping dictionary table code
	 * @return
	 */
//	String dicCode();
	
	/**
	 * mapping dictionary table groupcode
	 * @return
	 */
	String dictGroupCode() default "";

	/**
	 * specification table's code
	 * @return
	 */
    String dicText() default "";
    
    /**
     * use customized service to complete text convert
     * dictService: injected service name
     * dictServiceFunc: 
     * vars: List<String> param 
     * @UniAppDict(dictService="enhancerService", dictServiceFunc="useService", vars={"devCode","devClass","maintainPost"})
     * @see com.jeecg.devicemanage.wx.enhance.service.impl.EnhancerServiceImpl.useService(List<String>)
     * @return
     */
    String dictService() default "";
    String dictServiceFunc() default "";
    String[] vars() default "";
    

    /**
     * specification table
     * @return
     */
    String dictTable() default "";
}
