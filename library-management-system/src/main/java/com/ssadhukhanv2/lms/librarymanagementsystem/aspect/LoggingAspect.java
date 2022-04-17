package com.ssadhukhanv2.lms.librarymanagementsystem.aspect;

import com.ssadhukhanv2.lms.librarymanagementsystem.util.LibraryConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("@annotation(com.ssadhukhanv2.lms.librarymanagementsystem.aspect.LogResponse)")
    public void logTimings() {
    }

    @Around(value = "logTimings()")
    public Object logMethodCall(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        final long startTimeMillis = System.currentTimeMillis();
        Object returnObject = proceedingJoinPoint.proceed();
        final long endTimeMillis = System.currentTimeMillis();
        final double elapsedTimeMillis = endTimeMillis - startTimeMillis;
        final String methodName = proceedingJoinPoint.getSignature().getName();
        final Object[] argumentList = proceedingJoinPoint.getArgs();
        // Formatting Decimal Values
        // https://mkyong.com/java/how-to-round-double-float-value-to-2-decimal-points-in-java/
        final DecimalFormat decimalFormatForResponseTime = new DecimalFormat(LibraryConstants.DECIMAL_FORMAT_RESPONSE_TIME);
        String elapsedSeconds=decimalFormatForResponseTime.format((double)elapsedTimeMillis/1000);

        StringBuilder logMessage = new StringBuilder("Method: ");
        logMessage.append(methodName)
                .append("\tresponse time: ")
                .append(elapsedSeconds)
                .append(" seconds\targuments: [ | ");
        if (null != argumentList && argumentList.length > 0) {
            Arrays.asList(argumentList).stream().forEach(argument -> {
                logMessage.append(argument).append(" | ");
            });
        }
        logMessage.append("]")
                .append("\treturning: ");

        if (returnObject instanceof Collection<?>) {
            logMessage.append(((Collection) returnObject).size()).append(" instance(s)");
        } else {
            logMessage.append(returnObject.toString());
        }
        log.info(logMessage.toString());
        return returnObject;
    }
}
