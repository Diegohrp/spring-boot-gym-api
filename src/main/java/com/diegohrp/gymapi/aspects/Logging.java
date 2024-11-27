package com.diegohrp.gymapi.aspects;

import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Aspect
@Component
public class Logging {
    private static final Logger logger = LoggerFactory.getLogger(Logging.class);

    @Around("execution(* com.diegohrp.gymapi.controller.*.*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);
        String enpoint = request.getRequestURI();
        String requestMethod = request.getMethod();
        String body = getRequestBody(joinPoint);
        getRequestBody(joinPoint);
        logger.info("Transaction ID: {}, Received request: [Method: {}] [Endpoint: {}] [Body: {}]", transactionId, requestMethod, enpoint, body);

        try {
            ResponseEntity result = (ResponseEntity) joinPoint.proceed();
            Object resp = result.getBody() instanceof UserCreatedDto ?
                    new UserCreatedDto(((UserCreatedDto) result.getBody()).username(), "**********") :
                    result.getBody();

            logger.info("Transaction ID: {}, Completed request: Status: {}, Body: {}", transactionId, result.getStatusCode(), resp);
            return result;
        } catch (Throwable e) {
            logger.error("Transaction ID: {}, Error in request", transactionId);
            throw new RuntimeException(e);

        } finally {
            MDC.remove("transactionId");
        }
    }

    @Around("@annotation(com.diegohrp.gymapi.aspects.LoggableTransaction)")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String transactionId = MDC.get("transactionId");
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        logger.info("Transaction ID: {}, starts calling: {}.{}", transactionId, method.getDeclaringClass().getSimpleName(), method.getName());
        Object result = joinPoint.proceed();
        logger.info("Transaction ID: {}, ended calling: {}", transactionId, method);
        return result;
    }

    private String getRequestBody(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // Verify method's params
        Object[] args = joinPoint.getArgs();
        // Search for the param annotated with @RequestBody and get its value
        String requestBody = IntStream.range(0, method.getParameterCount())
                .filter(i -> method.getParameters()[i].isAnnotationPresent(RequestBody.class))
                .mapToObj(i -> args[i] != null ? args[i].toString() : "null")
                .findFirst()
                .orElse("No Body");
        return requestBody;
    }

}
