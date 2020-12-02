package com.example.aop;

import com.example.bean.DBContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
//利用spring的aop去根据方法的签名去识别是读操作还是写操作
public class DataSourceSelection {
    @Before("!@annotation(com.example.defineAnnotation.Master) " +
            "execution(* com.example.service.impl.*.*(..))")
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        if (StringUtils.startsWithAny(methodName, "get", "select", "find", "show")) {
            DBContextHolder.slave();
        }else {
            DBContextHolder.master();
        }
    }
}
