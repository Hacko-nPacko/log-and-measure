package com.example.logandmeasure.aspect;

import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.DefaultValueStyler;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created on 6.02.18.
 */
@Component
@Aspect
public class LoggingAspect {

    @Around("execution(public * *..service.*Service.*(..))")
    public Object aroundServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndReturn(joinPoint);
    }

    @Around("execution(public * *..controller.*..*Controller.*(..))")
    public Object aroundControllerMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndReturn(joinPoint);
    }

    @Around("execution(public * *..repository.*Repository.*(..))")
    public Object aroundRepositoryMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndReturn(joinPoint);
    }

    private Object logAndReturn(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Class returnType = signature.getReturnType();
        String name = signature.getName();
        String params = new ParamListBuilder(joinPoint, new ToStringCreator("", new DefaultValueStyler())).build();
        StopWatch timer = new StopWatch();
        timer.start();

        try {
            Object result = joinPoint.proceed();
            logger.debug("{} {}({}) returned {} [{}ms]", returnType.getSimpleName(), name, params, result, timer.getTime());
            return result;
        } catch (Throwable t) {
            logger.error("{} {}({}) threw {}({}) [{}ms]", returnType.getSimpleName(), name, params, t.getClass().getSimpleName(), t.getMessage(), timer.getTime());
            throw t;
        }
    }


    private static class ParamListBuilder {
        private final JoinPoint joinPoint;
        private final ToStringCreator toStringCreator;

        public ParamListBuilder(JoinPoint joinPoint, ToStringCreator toStringCreator) {
            this.joinPoint = joinPoint;
            this.toStringCreator = toStringCreator;
        }

        public String build() {
            String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
            if (parameterNames == null) {
                return toStringCreator.toString();
            }
            for (int i = 0; i < parameterNames.length; i++) {
                String parameterName = parameterNames[i];
                Object parameterValue = joinPoint.getArgs()[i];
                toStringCreator.append(parameterName, parameterValue);
            }
            return toStringCreator.toString();
        }
    }
}