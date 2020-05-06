package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class EmployeeLoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.example.demo.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.example.demo.dao.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint) {
        String theMethod = theJoinPoint.getSignature().toString();
        myLogger.info("=====> in @Before:calling method " + theMethod);

        Object[] args = theJoinPoint.getArgs();

        for(Object tempArg : args) {
            myLogger.info("=====> arguments: " + tempArg);
        }
    }
    @AfterReturning(pointcut = "forAppFlow()", returning ="theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=====> in @AfterReturning: from method: " + theMethod);
        myLogger.info("=====> result: " +theResult);
    }
}
