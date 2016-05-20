package com.advisorapp.api;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

    @Before("execution(public * com.advisorapp.api.controller.*Controller.*(..))")
    public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
        //System.out.println(":::::AOP Before REST call:::::" + pjp);
    }
}
