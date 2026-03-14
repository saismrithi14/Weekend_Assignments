package ServiceMembers.member.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import org.slf4j.Logger;


@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Before("execution(* ServiceMembers.member.service.*.*(..))")
    public void ServiceLayerLogging(JoinPoint joinpoint)
    {
        String methodName = joinpoint.getSignature().getName();
        logger.info("Service method: " + methodName + " executing");
        logger.info("Service Method: " + joinpoint.getSignature().getName() + " gets the arguments: " + Arrays.toString(joinpoint.getArgs()));
    }

    @Around("execution(* ServiceMembers.member.service.*.*(..))")

    public Object ServiceMethodExecutionTime(ProceedingJoinPoint joinpoint) throws Throwable
    {
        long start = System.currentTimeMillis();
        Object result = joinpoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("Service method " + joinpoint.getSignature().getName() + " took " + (end - start) + "ms");
        return result;
    }

    @AfterThrowing(pointcut = "execution(* ServiceMembers.member.service.*.*(..))", throwing = "ex")
    public void handlingException(JoinPoint joinpoint, Throwable ex)
    {
        logger.error("An exception occured while running the service method " + joinpoint.getSignature().getName());
        logger.error("Error message: " + ex.getMessage());
    }

    @AfterReturning(pointcut="execution(* ServiceMembers.member.service.*.*(..))", returning = "result")
    public void loggingReturnValue(JoinPoint joinpoint, Object result)
    {
        logger.info("Service method " + joinpoint.getSignature().getName() + " returned " + result);
    }

    //CONTROLLER METHODS ADVICES AND POINTCUT EXPRESSIONS

    @Before("execution(* ServiceMembers.member.controller.*.*(..))")
    public void logController(JoinPoint joinpoint)
    {
        logger.info("Controller Method " + joinpoint.getSignature().getName() + " is executing");
        logger.info("Controller Method: " + joinpoint.getSignature().getName() + " gets the arguments: " + Arrays.toString(joinpoint.getArgs()));
    }

    @Around("execution(* ServiceMembers.member.controller.*.*(..))")
    public Object captureControllerExecutionTime(ProceedingJoinPoint joinpoint) throws Throwable
    {
        long start = System.currentTimeMillis();
        Object result = joinpoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("Controller method: "+ joinpoint.getSignature().getName() + " took " + (end-start) + "ms");
        return result;
    }

    @AfterReturning(pointcut = "execution(* ServiceMembers.member.controller.*.*(..))", returning = "result")
    public void logReturnValue(JoinPoint joinpoint, Object result)
    {
        logger.info("Controller method " + joinpoint.getSignature().getName() + " returned: " + result);
    }

    @AfterThrowing(pointcut = "execution(* ServiceMembers.member.controller.*.*(..))", throwing = "ex")
    public void ControllerException(JoinPoint joinpoint, Throwable ex)
    {
        logger.error("An exception occurred while running the controller method: " + joinpoint.getSignature().getName());
        logger.error("Error message: " + ex.getMessage());

    }




}
