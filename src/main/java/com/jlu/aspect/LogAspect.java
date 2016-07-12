package com.jlu.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2016/7/4.
 * 自定义一个打log的切面，在调用controller包中的方法入口前后都打log
 * 这只是一个演示Spring的AOP的示例
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.jlu.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs()){
            if(arg != null) {
                sb.append("arg:" + arg.toString() + "|");
            }
        }
        log.info("before method:"+sb.toString());
    }

    @After("execution(* com.jlu.controller.*Controller.*(..))")//使用 * 通配符对controller包下面的所有方法都加这个切面
    public void afterMethod(JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        for(Object arg : joinPoint.getArgs()){
            if(arg != null) {
                sb.append("arg:" + arg.toString() + "|");
            }
        }
        log.info("after method:"+sb.toString());
    }
}
