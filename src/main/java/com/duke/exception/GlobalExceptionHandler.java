package com.duke.exception;

import com.duke.pojo.ApiResult;
import com.duke.util.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求异常处理类
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ApiResult exceptionHandler(HttpServletRequest request, Exception e){
        return ExceptionUtils.handle(e);
    }
}
