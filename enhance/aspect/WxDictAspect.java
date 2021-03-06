package com.jeecg.devicemanage.wx.enhance.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeecg.devicemanage.wx.enhance.anno.UniAppDict;
import com.jeecg.devicemanage.wx.enhance.service.EnhancerService;
import com.jeecg.devicemanage.wx.enhance.vo.DictVo;
import com.jeecg.devicemanage.wx.enhance.vo.Result;


/**
 * in wechat select data case:
 * add extra attribute to target result 
 * before service return to controller 
 * 
 * @author renqing
 *
 * Jun 16, 2021
 */
@Aspect
@Component
public class WxDictAspect implements ApplicationContextAware{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WxDictAspect.class);
	
	private static final String DICT_SUFFIX_TXT = "_dictTxt";
	private static final String DICT_SUFFIX_SELOP = "_dictGroup";
	
	private ApplicationContext context;
	
	@Autowired
	private EnhancerService enhancerService;
	
    @Pointcut("execution(public com.jeecg.devicemanage.wx.enhance.vo.Result com.jeecg.devicemanage.wx.orderinfo.controller.*.*(..))")
    public void loMeinNeedsShrimp() {}

    @Around("loMeinNeedsShrimp()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	long time1=System.currentTimeMillis();
    	Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        logger.debug("?????????????????????"+(time2-time1)+"ms");

        long start=System.currentTimeMillis();
//        JsonArray jsonarr = new JsonArray();
        // finally result
        JSONObject jsonRes = new JSONObject();
        // fixed original data set
        JSONArray contentHolder = new JSONArray();
        // dictionary result
        JSONObject dictRes = new JSONObject();
        
        Set<String> keyDict = new HashSet<String>();
        
        // data process logic
        if (result instanceof Result) {
        	Result<?> res = (Result<?>) result;
        	Integer code = res.getCode();
        	
        	// status code == 200?
        	if (code == 200) {
        		List<?> list = res.getResult();
        		
        		// data list is not null?
        		if (list.size() > 0) {
        			for (Object obj : list) {
        				
        				// if list's each obj is a string instance skip translation, return current object directly
        				if (obj instanceof String) {
        					break;
        				}
        				// extract all fields
        				Field[] fds = getAllFields(obj);
        				
        				// convert obj to jsonObject
//        				String tempJson = new Gson().toJson(obj);// ??????json?????????.
//        				JsonObject jsonObject = new JsonParser().parse(tempJson).getAsJsonObject();// ??????json??????
        				JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(obj));
        				for (Field field : fds) {
        					UniAppDict an = field.getAnnotation(UniAppDict.class); 
        					if (null != an) {
        						String dictService = an.dictService();
        						String dictServiceFunc = an.dictServiceFunc();
        						// invoke specified service, privileged
        						if (StringUtils.isNotEmpty(dictService) && StringUtils.isNotEmpty(dictServiceFunc)) {
        							Object bean = context.getBean(dictService);
        							if (null != bean) {
        								// object filed names, sequentially assembled as a array
        								// use each value as service input parameter
        								String[] vars = an.vars();
        								List<String> inputVar = new ArrayList<>();
        								for (String varItem : vars) {
        									String codeVal = getFieldValueByName(varItem, obj);
        									inputVar.add(codeVal);
        								}
        								Method func = bean.getClass().getDeclaredMethod(dictServiceFunc, List.class);
        								Object serviceRes = func.invoke(bean, inputVar);
        								jsonObject.put(field.getName()+DICT_SUFFIX_TXT, serviceRes);
        							}
        						} else {
        							// use data dictionary, secondary
        							String dictGroupCode = an.dictGroupCode();
        							String codeVal = getFieldValueByName(field.getName(), obj);
        							String text = enhancerService.simpleSelect(dictGroupCode, codeVal);
        							jsonObject.put(field.getName()+DICT_SUFFIX_TXT, text);
        							
        							// remove duplicate select options
        							if (keyDict.add(field.getName()+DICT_SUFFIX_SELOP)) {
        								List<DictVo> selOpt = enhancerService.selectionSel(dictGroupCode);
        								dictRes.put(field.getName()+DICT_SUFFIX_SELOP, selOpt);
        							}
//        						jsonObject.add(field.getName()+DICT_SUFFIX_TXT, new Gson().toJsonTree(text));
//        						jsonObject.add(field.getName()+DICT_SUFFIX_SELOP, new Gson().toJsonTree(selOpt));
        						}
        					}        					
        				}
        				
        				contentHolder.add(jsonObject);
        			}
        			
        			jsonRes.put("list", contentHolder);
        			jsonRes.put("dict", dictRes);
        			res.setData(jsonRes);
        		} else {
        			// status code is 200
        			// list size is nil
        			long end=System.currentTimeMillis();
                    logger.debug("?????????????????????????????????"+(end-start)+"ms");
                    res.setMessage("????????????");
        		}
        		
        	} else {
        		// not 200 status code
        		long end=System.currentTimeMillis();
        		logger.debug("?????????????????????????????????"+(end-start)+"ms");
                res.setMessage("???????????????????????????????????????");
        	}
        	long end=System.currentTimeMillis();
            logger.debug("????????????????????????"+(end-start)+"ms");
        } else {
        	// not Result return type
        	// principally will not occur
        	// because of the aop handle Result return type only
        	long end=System.currentTimeMillis();
        	logger.debug("?????????????????????????????????"+(end-start)+"ms");
        }
        
        return result;
    }
    
    /**
     * extract all fields
     * @param object
     * @return
     */
    private Field[] getAllFields(Object object) {
    	Class<?> clazz = object.getClass();
		List<Field> fieldList = new ArrayList<>();
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		Field[] fields = new Field[fieldList.size()];
		fieldList.toArray(fields);
		return fields;
    }
    
    /**
     * reflect getter
     * @param fieldName
     * @param o
     * @return
     */
    private String getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            String value = (String) method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
