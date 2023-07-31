package com.ecom.task.common.limitter;

import com.ecom.task.common.exception.OverLimitException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimiterAspect {
    private final RateLimiterConfiguration rateLimiterConfiguration;
    private final Map<String, RateLimiter> requestsMap = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimited) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        var userAddress = request.getRemoteAddr();
        var rateLimiter = requestsMap.computeIfAbsent(request.getRemoteAddr(),
                k -> new RateLimiter(rateLimiterConfiguration.getRequestLimit(), rateLimiterConfiguration.getRequestLimit()));

        if (rateLimiter.checkRequest()) {
            return joinPoint.proceed();
        } else {
            throw new OverLimitException(String.format("user address: [%s]", userAddress));
        }
    }
}
