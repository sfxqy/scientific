package cn.hsernos.common.aop;


import cn.hsernos.common.beans.ResultBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 处理和包装异常
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public cn.hsernos.common.beans.ResultBean *(..))")
    public void controllerMethod() {
    }

    @Around("controllerMethod()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        result = (ResultBean<?>) pjp.proceed();
        logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        return result;
    }

}
