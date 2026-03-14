package ServiceReport.reports.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //Writing the logging aspects for the services
    @Before("execution(* ServiceReport.reports.controller.*.*(..))")
    public void loggingController(JoinPoint joinPoint)
    {
        logger.info("Controller method: " + joinPoint.getSignature().getName() + " is running");
        logger.info("Arguments given to the controller method: " + joinPoint.getSignature().getName() + ": " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("execution(* ServiceReport.reports.controller.*.*(..))")
    public Object loggingControllerExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        long start_time = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end_time = System.currentTimeMillis();
        logger.info("Time taken for the controller method: " + joinPoint.getSignature().getName() + " is " + (end_time - start_time) + "ms");
        return result;
    }

    @AfterReturning(pointcut = "execution(* ServiceReport.reports.controller.*.*(..))", returning="result")
    public void loggingControllerReturnValues(JoinPoint joinPoint, Object result)
    {
        System.out.println("Service method: " + joinPoint.getSignature().getName() + " returns " + result.toString());
    }

    @AfterThrowing(pointcut = "execution(* ServiceReport.reports.controller.*.*(..))", throwing="ex")
    public void loggingControllerErrors(JoinPoint joinPoint, Throwable ex)
    {
        System.out.println("Service method: " + joinPoint.getSignature().getName() + "returned the exception: " + ex.getMessage());
    }

    //Logging for the controller methods as well
    //Writing the logging aspects for the services
    @Before("execution(* ServiceReport.reports.service.*.*(..))")
    public void loggingServices(JoinPoint joinPoint)
    {
        logger.info("Service method: " + joinPoint.getSignature().getName() + " is running");
        logger.info("Arguments given to the service method: " + joinPoint.getSignature().getName() + ": " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("execution(* ServiceReport.reports.service.*.*(..))")
    public Object loggingServiceExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable
    {
        long start_time = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end_time = System.currentTimeMillis();
        logger.info("Time taken for the service method: " + joinPoint.getSignature().getName() + " is " + (end_time - start_time) + "ms");
        return result;
    }

    @AfterReturning(pointcut = "execution(* ServiceReport.reports.service.*.*(..))", returning="result")
    public void loggingServiceReturnValues(JoinPoint joinPoint, Object result)
    {
        System.out.println("Service method: " + joinPoint.getSignature().getName() + " returns " + result.toString());
    }

    @AfterThrowing(pointcut = "execution(* ServiceReport.reports.service.*.*(..))", throwing="ex")
    public void loggingServiceErrors(JoinPoint joinPoint, Throwable ex)
    {
        System.out.println("Service method: " + joinPoint.getSignature().getName() + "returned the exception: " + ex.getMessage());
    }


}
