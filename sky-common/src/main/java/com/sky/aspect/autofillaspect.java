package com.sky.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自定义切面类
 */
@Aspect
@Component
@Slf4j
public class autofillaspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.autofill)")
    public void autofillpointcut(){

    }

    /**
     * 公共字段填充
     */
    @Before("autofillpointcut()")
    public void autofill(JoinPoint joinPoint){
        log.info("开始执行公共字段 填充");
    }
}
