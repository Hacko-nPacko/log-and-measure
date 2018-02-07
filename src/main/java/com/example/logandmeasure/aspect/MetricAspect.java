package com.example.logandmeasure.aspect;

import com.example.logandmeasure.Measure;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 5/12/17.
 */
@Component
@Aspect
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MetricAspect {

    private final Measure measure;

    @Around("execution(public * *..service.*Service.*(..))")
    public Object service(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    @Around("execution(public * *..controller.*Controller.*(..))")
    public Object controller(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    @Around("execution(public * *..client.*Client.*(..))")
    public Object client(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    @Around("execution(public * *..client.Clients.*(..))")
    public Object clients(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    @Around("execution(public * *..controller.CoreOneEndpointController.*(..))")
    public Object endpoint(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    @Around("execution(public * *..proxy.*Proxy.*(..))")
    public Object proxy(ProceedingJoinPoint joinPoint) throws Throwable {
        return metric(joinPoint);
    }

    private Object metric(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result;
        long start = System.currentTimeMillis();
        try {
            measure.call(joinPoint);
            result = joinPoint.proceed();
            measure.success(joinPoint);
        } catch (Throwable up) {
            measure.error( joinPoint, up);
            throw up;
        } finally {
            measure.time(joinPoint, start);
        }
        return result;
    }

}
