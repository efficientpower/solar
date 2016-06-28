package org.wjh.solar.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类
 * 
 * @author wangjihui
 *
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        // TODO Auto-generated method stub
        SpringContextUtils.context = context;
    }

    /**
     * 根据提供的bean名称得到相应的服务类
     * 
     * @param name
     *            bean name.
     * @return the instance of bean.
     */
    public static Object getBean(String name) {
        return context.getBean(name);
    }

    /**
     * 根据提供的bean class得到相应的服务类
     * 
     * @param <T>
     *            bean type of class.
     * @param clazz
     *            bean class.
     * @return the instance of bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
