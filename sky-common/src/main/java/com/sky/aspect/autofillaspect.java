package com.sky.aspect;


import com.sky.annotation.autofill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

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
    public void autofill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("开始执行公共字段 填充");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        autofill annotation = signature.getMethod().getAnnotation(autofill.class);
        if (annotation == null){
            return;
        }else{
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0){
                return;
            } else {
                Object arg = args[0];
                LocalDateTime now = LocalDateTime.now();
                Long currentId = BaseContext.getCurrentId();
                if (annotation.value() == OperationType.INSERT){
                    Method setCreateTime = arg.getClass().getMethod("setCreateTime", LocalDateTime.class);
                    Method setCreateUser = arg.getClass().getMethod("setCreateUser", Long.class);
                    Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
                    //getmethod()获取的是公共方法, getDeclaredMethod()获取的是所有方法,无论是否公有或者私有
                    Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Long.class);
                    setCreateTime.invoke(arg,now);
                    setCreateUser.invoke(arg,currentId);
                    setUpdateTime.invoke(arg,now);
                    setUpdateUser.invoke(arg,currentId);
                }else if (annotation.value() == OperationType.UPDATE){
                    Method setUpdateTime = arg.getClass().getMethod("setUpdateTime", LocalDateTime.class);
                    Method setUpdateUser = arg.getClass().getMethod("setUpdateUser", Long.class);
                    setUpdateUser.invoke(arg,currentId);
                    setUpdateTime.invoke(arg,now);
                }

            }

        }



    }
}
