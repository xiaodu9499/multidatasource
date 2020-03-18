package com.github.xjs.multidatasource.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {

    /**
     * 定义切入点：
     *  有@DataSourceAnnotation注解的方法
     */
    @Pointcut("@annotation(com.mamcharge.techc.demo.config.DataSourceAnnotation) || @within(com.mamcharge.techc.demo.config.DataSourceAnnotation)")
    public void serviceMethodPointcut(){}

    /**
     *  数据源设置
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("serviceMethodPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        try {
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            DataSourceAnnotation useDataSource= methodSignature.getMethod().getAnnotation(DataSourceAnnotation.class);
            if(useDataSource==null){
                useDataSource= (DataSourceAnnotation) methodSignature.getDeclaringType().getAnnotation(DataSourceAnnotation.class);
            }
            DynamicDataSourceHolder.setCurrentDateSourceName(useDataSource.name());
            result = pjp.proceed();
        } catch (Exception e) {
            throw e;
        }finally {
            DynamicDataSourceHolder.clearCurrentDateSourceName();
        }
        return result;
    }

}
