package com.example.logandmeasure;

import com.timgroup.statsd.StatsDClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created on 5/22/17.
 */
@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class Measure {

    private final StatsDClient statsd;

    public void call(ProceedingJoinPoint joinPoint) {
        statsd.increment(String.join(".", getName(joinPoint), "call"));
    }

    public void success(ProceedingJoinPoint joinPoint) {
        statsd.increment(String.join(".", getName(joinPoint), "success"));
    }

    public void error(ProceedingJoinPoint joinPoint, Throwable up) {
        statsd.increment(String.join(".", getName(joinPoint), "error", up.getClass().getSimpleName()));
    }

    public void time(ProceedingJoinPoint joinPoint, long start) {
        statsd.recordExecutionTimeToNow(getName(joinPoint), start);
    }

    public void time(String metric, long time) {
        statsd.time(metric, time);
    }

    public void inc(String metric) {
        statsd.increment(metric);
    }

    private String getName(ProceedingJoinPoint joinPoint) {
        if (joinPoint instanceof MethodInvocationProceedingJoinPoint) {
            MethodInvocationProceedingJoinPoint point = (MethodInvocationProceedingJoinPoint) joinPoint;
            Signature signature = point.getSignature();
            return String.format("%s.%s", signature.getDeclaringType().getCanonicalName(), signature.getName());
        }
        return joinPoint.toShortString();
    }


}
