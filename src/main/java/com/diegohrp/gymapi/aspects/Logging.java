package com.diegohrp.gymapi.aspects;

import com.diegohrp.gymapi.dto.user.ChangePasswordDto;
import com.diegohrp.gymapi.dto.user.LoginUserDto;
import com.diegohrp.gymapi.dto.user.UserCreatedDto;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.stream.IntStream;


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
        String body = getRequestBody(joinPoint).toString();

        logger.info("Transaction ID: {}, Received request: [Method: {}] [Endpoint: {}] [Body: {}]", transactionId, requestMethod, enpoint, body);

        try {
            ResponseEntity result = (ResponseEntity) joinPoint.proceed();
            Object resp = result.getBody() instanceof UserCreatedDto ?
                    new UserCreatedDto(((UserCreatedDto) result.getBody()).username(), "**********") :
                    result.getBody();

            logger.info("Transaction ID: {}, Completed request: Status: {}, Body: {}", transactionId, result.getStatusCode(), resp);
            return result;
        } catch (Throwable e) {
            if (e instanceof HttpClientErrorException) {
                logger.warn("Transaction ID: {}, Client Error: {}", transactionId, e.getMessage());
            } else {
                logger.error("Transaction ID: {}, Error in request", transactionId);
            }
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
        logger.info("Transaction ID: {}, ended calling: {}.{}", transactionId, method.getDeclaringClass().getSimpleName(), method.getName());
        return result;
    }

    private Object getRequestBody(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod(); // Verify method's params
        Object[] args = joinPoint.getArgs();
        // Search for the param annotated with @RequestBody and get its value
        Object requestBody = IntStream.range(0, method.getParameterCount())
                .filter(i -> method.getParameters()[i].isAnnotationPresent(RequestBody.class))
                .mapToObj(i -> args[i] != null ? args[i] : "null")
                .findFirst()
                .orElse("No Body");
        //Remove sensitive fields
        if (requestBody instanceof LoginUserDto) {
            requestBody = new LoginUserDto(((LoginUserDto) requestBody).username(), "**********");
        }
        if (requestBody instanceof ChangePasswordDto) {
            requestBody = new ChangePasswordDto(
                    ((ChangePasswordDto) requestBody).username(),
                    "**********",
                    "**********");
        }
        return requestBody;
    }

}
