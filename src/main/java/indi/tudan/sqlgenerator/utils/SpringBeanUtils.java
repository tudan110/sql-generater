package indi.tudan.sqlgenerator.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring 上下文工具类
 *
 * @author wangtan
 * @date 2019年06月25日 16:02:42
 * @since 1.0
 */
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    /**
     * spring 上下文
     */
    private static ApplicationContext applicationContext;

    /**
     * 获取 spring 上下文
     *
     * @return spring 上下文
     * @date 2019年06月25日 16:05:26
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 设置 spring 上下文
     *
     * @param applicationContext spring 上下文
     * @throws BeansException 异常
     * @date 2019年06月25日 16:05:20
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    /**
     * 获取已注入的 bean，返回 Object
     *
     * @param beanName 已注入对象名称
     * @return 已注入对象
     * @date 2019年06月25日 16:06:04
     */
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    /**
     * 获取已注入的 bean，返回泛型
     *
     * @param clazz 已注入对象的类类型对象
     * @param <T>   泛型
     * @return 已注入泛型对象
     * @date 2019年06月25日 16:06:33
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param beanName 已注入对象名称
     * @param clazz    已注入对象的类类型对象
     * @param <T>      泛型
     * @return 已注入泛型对象
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return applicationContext.getBean(beanName, clazz);
    }

}