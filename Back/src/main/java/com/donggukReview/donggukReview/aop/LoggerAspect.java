package com.donggukReview.donggukReview.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class LoggerAspect {

	@Pointcut("execution(* com.donggukReview.donggukReview..controller.*Controller.*(..)) || execution(* com.donggukReview.donggukReview..service.*Impl.*(..)) || execution(* com.donggukReview.donggukReview..mapper.*Mapper.*(..))")
	private void loggerTarget() {
		
	}
	
	@Around("loggerTarget()") 
	public Object logPrinter(ProceedingJoinPoint joinPoint) throws Throwable {
		String type = "";
		String className = joinPoint.getSignature().getDeclaringTypeName();
		if (className.indexOf("Controller") > -1) {
			type = "[Controller]";
		} else if (className.indexOf("ServiceImpl") > -1) {
			type = "[ServiceImpl]";
		}
		
		String methodName = joinPoint.getSignature().getName();
		
		log.debug(type + " " + className + "." + methodName);
		return joinPoint.proceed();
	}
}
