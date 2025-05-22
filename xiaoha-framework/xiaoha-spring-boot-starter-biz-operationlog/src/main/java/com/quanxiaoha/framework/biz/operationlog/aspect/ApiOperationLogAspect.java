package com.quanxiaoha.framework.biz.operationlog.aspect;

import com.quanxiaoha.framework.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Aspect
@Slf4j
public class ApiOperationLogAspect {

    @Pointcut("@annotation(com.quanxiaoha.framework.biz.operationlog.aspect.ApiOperationLog)")
    public void apiOperationLog(){}

    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // 获取请求的类和方法
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        // 请求入参
        Object[] args = joinPoint.getArgs();
        String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));
        String apiOperationLogDescription = getApiOperationLogDescription(joinPoint);

        log.info("========== 请求开始：[{}], 入参：[{}], 请求类：[{}], 请求方法：[{}] ==========",
                apiOperationLogDescription, argsJsonStr, className, methodName);

        Object result = joinPoint.proceed();

        long executeTime = System.currentTimeMillis() - startTime;

        log.info("========== 请求开始：[{}], 执行时间：[{}], 出参：[{}] ==========",
                apiOperationLogDescription, executeTime, JsonUtils.toJsonString(result));

        return result;
    }

    /**
     * 获取注解描述信息
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);
        return apiOperationLog.description();
    }


    private Function<Object, String> toJsonStr() {
        return JsonUtils::toJsonString;
    }


}
