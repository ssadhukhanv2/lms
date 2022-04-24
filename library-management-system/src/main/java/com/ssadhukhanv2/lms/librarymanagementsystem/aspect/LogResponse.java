package com.ssadhukhanv2.lms.librarymanagementsystem.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *
 * Methods annotated with LogReponse will be logged by {@link LoggingAspect}
 *
 * @author Subhrajit Sadhukhan
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogResponse {
}
