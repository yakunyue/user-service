/*
 * Copyright 2017 - 数多科技
 * 
 * 北京数多信息科技有限公司 本公司保留所有下述内容的权利。 本内容为保密信息，仅限本公司内部使用。 非经本公司书面许可，任何人不得外泄或用于其他目的。
 */
package com.fxg.userservice.config;

import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * 该类为AOP中的Aspect（切面类），主要处理各子服务间API调用的日志记录,包括输入参数、输出参数、执行时长等等
 * </p>
 *
 * @author zhangmingming<zhangmingming01@we.com>
 * @version v1.0
 * @datetime 2017年8月18日 下午5:59:18
 * @since 1.8
 */
@Aspect
@Component
public class ApiLogAspect {

    private Logger logger = LoggerFactory.getLogger(ApiLogAspect.class);



    @Pointcut("execution(public * com.fxg..*.controller..*.*ontroller.*(..))")
    public void controller() {
    }

    //只需要这一个类就行了，其他类不提供相应的方法
//    @Pointcut("execution(public * com.fxg.spring.basecontroller.AbstractController.*(..))")
//    public void baseController() {
//    }

    //先拼接再输出，确保输出结果在一段文字里，而不是分散开
//    @Around(value = "controller() || baseController()")
    @Around(value = "controller()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.nonNull(attributes)) {
            Object[] args=pjp.getArgs();
            HttpServletRequest request = attributes.getRequest();
            String url=request.getRequestURL().toString();
            String uuid= UUID.randomUUID().toString();
            StringBuilder sb = new StringBuilder();
            sb.append("\n===========================================================\n");
            sb.append(String.format("=原始请求数据 请求惟一号 %s\n",uuid));
            sb.append(String.format("=url : %s\n",url));
//            sb.append(String.format("=request method : %s\n",request.getMethod()));
//            sb.append(String.format("=remote ip : %s\n",request.getRemoteAddr()));
//            sb.append(String.format("=class method : %s\n",pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()));
            for (int i = 0; i <args.length ; i++) {
                if (Objects.isNull(args[i]))
                {
                    sb.append(String.format("=param index %s : %s\n",i, "null"));
                }
                else
                {
                    //这里不用json了，因为可能是一些特殊参数，无法序列化的，比如httprequest等
                    sb.append(String.format("=param index %s : %s\n",i, args[i].toString()));
                }

            }
            sb.append("===========================================================");
            logger.info(sb.toString());
            long startTime = System.currentTimeMillis();
            Object result = pjp.proceed();
            long endTime = System.currentTimeMillis();
            sb = new StringBuilder();
            sb.append("\n===========================================================\n");
            sb.append(String.format("=执行结果及分析数据 uuid %s url %s\n",uuid, url));
            sb.append(String.format("=%s execute time :%s ms\n", pjp.getSignature(), endTime - startTime));
            sb.append(String.format("=response : %s\n", result));
            sb.append("===========================================================");
            logger.info(sb.toString());
            return result;
        }
        else
        {
            return pjp.proceed();
        }
    }
}
