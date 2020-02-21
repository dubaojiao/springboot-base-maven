package com.duke.aop;

import com.duke.pojo.ApiResult;
import com.duke.task.AsyncTaskService;
import com.duke.util.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Title  Aop 日志
 * @ClassName  WebControllerAop
 * @Author duke
 * @Date 2018/9/13
 */
@Component
@Aspect
public class WebControllerAop {

    private static final Logger logger = LoggerFactory.getLogger(WebControllerAop.class);

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired
    AsyncTaskService asyncTaskService;

    //匹配com..xchxin.simons.controller包及其子包下的所有类的所有方法
    @Pointcut("execution(* com.duke.api..*.*(..))")
    public void executeService() {

    }

    /**
     * 拦截器具体实现
     *
     * @param pjp
     * @return JsonResult（被拦截方法的执行结果，或需要登录的错误提示。）
     */
    @Around("executeService()") //指定拦截器规则；也可以直接把“execution(* com.............)”写进这里
    public Object Interceptor(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();

        Object[] args = pjp.getArgs();

        logger.info("当前请求的参数是{}", args);
        ApiResult apiResult = null;
        try {
            Object result =  pjp.proceed();

            apiResult = ApiResult.returnSuccess("成功",result);

        }catch (Exception ex){
            apiResult =  ExceptionUtils.handle(ex);
        }finally {
            long endTime = System.currentTimeMillis();

            logger.info("[{}]请求结束，耗时[{}]ms,参数{},返回结果[{}]",
                    methodName,endTime-startTime,args,apiResult);
        }

        return apiResult;
    }


}
