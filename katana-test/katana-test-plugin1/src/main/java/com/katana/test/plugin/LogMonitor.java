package com.katana.test.plugin;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.stereotype.Component;

/**
 * Created on 2022/11/3
 *
 * @author liyifei
 */
@Aspect
@Component
@Slf4j
public class LogMonitor {

    @Around("@within(com.katana.test.plugin.Plugin)")
    @SneakyThrows
    public Object log(ProceedingJoinPoint joinPoint) {
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            if (!methodSignature.getMethod().getName().equals("toString")) {
                log.info("==>[logV1]before {}....", joinPoint);
                Object res = joinPoint.proceed();
                log.info("==>[logV1]after {}....", joinPoint);
                return res;
            }
        }
        return joinPoint.proceed();
    }
}
