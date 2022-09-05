package com.technokratos.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("@within(org.springframework.stereotype.Service)")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Object result = joinPoint.proceed();

        String template = "Method: {}(" +
                StringUtils.repeat( "{} ", args.length) +
                ") return: {}";

        log.info(template, signature.getName(), args, result);
        return result;
    }
}
