package org.wjh.solar.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;

/**
 * bean和map转换类
 * 
 * @author wangjihui
 *
 */
public class BeanMapUtils {
	/**
	 * bean转map
	 * 
	 * @param bean
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public static Map<String, Object> beanToMap(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Map<String, Object> map = new LinkedHashMap<String, Object>(0);
		PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
		PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(bean);
		for (int i = 0; i < descriptors.length; i++) {
			String name = descriptors[i].getName();
			if (!StringUtils.equals(name, "class")) {
				map.put(name, propertyUtilsBean.getNestedProperty(bean, name));
			}
		}
		return map;
	}
	
	/**
	 * map 转Object
	 * 
	 * @param map
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object mapToBean(Map<String, Object> map) throws IllegalAccessException, InvocationTargetException{
		if(map == null){
			return null;
		}
		Object obj = new Object();
		BeanUtils.populate(obj, map);
		return obj;
	} 
	
}
