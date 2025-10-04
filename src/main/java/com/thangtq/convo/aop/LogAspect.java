package com.thangtq.convo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
  private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

  public LogAspect() {}

  /*
   * Point Cut Definition
   * */

  @Pointcut("execution(public * com.springboot.convert.service.impl.ItemServiceImpl.*(..))")
  public void logAllItemServiceMethod() {}

  @Pointcut(
      "execution(public * com.springboot.convert.service.impl.ItemServiceImpl.*(com.springboot.convert.model.CreateItemRequest))")
  public void logItemServiceModifiedMethod() {}

  /*
   * Advice Definition
   * */

  @Before("logAllItemServiceMethod()")
  public void logBeforeMethod(JoinPoint joinPoint) {
    Signature signatureMethod = joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();

    if (args.length > 0) {
      logger.info(
          "After - The method {} is prepared run with parameters {}",
          signatureMethod.getName(),
          joinPoint.getArgs());
    } else {
      logger.info(
          "After - The method {} is prepared run with no parameters", signatureMethod.getName());
    }
  }

  @After("logAllItemServiceMethod()")
  public void logAfterMethod(JoinPoint joinPoint) {
    Signature signatureMethod = joinPoint.getSignature();
    Object[] args = joinPoint.getArgs();

    if (args.length > 0) {
      logger.info(
          "After - The method {} is completed to run with parameters {}",
          signatureMethod.getName(),
          joinPoint.getArgs());
    } else {
      logger.info(
          "After - The method {} is completed to run with no parameters",
          signatureMethod.getName());
    }
  }

  @Around("logItemServiceModifiedMethod()")
  public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    String methodName = joinPoint.getSignature().getName();
    Object[] args = joinPoint.getArgs();

    logger.info("Around - Before execution of method {}. Args: {}", methodName, args);

    Object result = null;
    try {
      result = joinPoint.proceed(); // Execute the actual method
      logger.info(
          "Around - After successful execution of method {}. Returned: {}", methodName, result);
    } catch (Throwable ex) {
      logger.error("Around - Method {} threw an exception: {}", methodName, ex.getMessage());
      throw ex; // Re-throw the exception to maintain original flow
    } finally {
      long endTime = System.currentTimeMillis();
      long costTime = endTime - startTime;
      logger.info("Around - Method {} cost {} ms to execute.", methodName, costTime);
    }
    return result;
  }
}
