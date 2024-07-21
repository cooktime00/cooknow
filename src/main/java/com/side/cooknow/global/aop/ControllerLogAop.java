package com.side.cooknow.global.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Aspect
@Component
public class ControllerLogAop {

    @Pointcut("execution(* com.side.cooknow.domain.*.controller.*.*(..))")
    private void pointCut() {
    }

    @Before("pointCut()")
    public void beforeRequest(JoinPoint joinPoint) {
        Class<?> declaringClass = getClass(joinPoint);
        Method method = getMethod(joinPoint);
        log.info("==== Request {} - {} ====", declaringClass.getName(), method.getName());

        // 파라미터 받아오기
        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) log.info("no parameter");
        for (Object arg : args) {
            log.info("==== {} - {} ====", arg.getClass().getSimpleName(), arg);
        }
    }

    // Poincut에 의해 필터링된 경로로 들어오는 경우 메서드 리턴 후에 적용
    @AfterReturning(value = "pointCut()", returning = "returnObj")
    public void afterResponse(JoinPoint joinPoint, Object returnObj) {
        // 메서드 정보 받아오기
        Class<?> declaringClass = getClass(joinPoint);
        Method method = getMethod(joinPoint);
        log.info("==== Response {} - {} ====", declaringClass.getName(), method.getName());
        log.info("return value = {}", returnObj);
    }

    @Around("pointCut()")
    public Object logRequestResponseTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Proceed with the original method execution
        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        // Calculate the time taken
        long elapsedTime = endTime - startTime;

        // Log the request and response time
        String methodName = joinPoint.getSignature().toShortString();

        log.info("Method " + methodName + " took " + elapsedTime + "ms");
        return result;
    }


    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private Class<?> getClass(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getDeclaringType();
    }
}
