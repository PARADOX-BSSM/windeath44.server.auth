package com.example.auth.global.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static AtomicLong counter = new AtomicLong();

    @Pointcut("execution(* com.example.auth.domain.auth.service.*.*(..))")
    public void authServiceMethodLogging() {}

    @Pointcut("execution(* com.example.auth.domain.mail.service.*.*(..))")
    public void mailServiceMethodLogging() {}

    @Pointcut("execution(* com.example.auth.domain.gRPC.service.*.*(..))")
    public void grpcServiceMethodLogging() {}

    @Around("authServiceMethodLogging() && mailServiceMethodLogging() && grpcServiceMethodLogging()")
    public Object serviceMethodLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.trace("Entering {} by {}", joinPoint.getSignature().getName(), counter.get());
        Object result = joinPoint.proceed();
        log.trace("Exiting {} by {}", joinPoint.getSignature().getName(), counter.getAndIncrement());
        return result;
    }
}
