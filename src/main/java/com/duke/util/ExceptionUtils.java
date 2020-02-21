package com.duke.util;

import com.duke.exception.ApiException;
import com.duke.pojo.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageConversionException;

import javax.validation.constraints.NotNull;

/**
 * Exception具类
 * @author YunBo
 * @date   2018/01/05
 */
public final class ExceptionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionUtils.class);

    @NotNull
    public static String record(String token, Exception ex) {
        logger.error(token + ex.getMessage(), ex);
        return "";
    }

    public static String record(String token, String exceptionMessage) {
        return record(token, new Exception(exceptionMessage));
    }

    public static ApiResult handle(Throwable ex) {
        logger.error("捕获异常", ex);
        if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            return ApiResult.returnError("请求接口不存在");
        }
        if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
            return ApiResult.returnError("请求方法不受支持");
        }
        if (ex instanceof HttpMessageConversionException) {
            return ApiResult.returnError("请求内容或类型有误");
        }
        if(ex instanceof ApiException){
            ApiException apiException = (ApiException) ex;
            return ApiResult.returnByCode(apiException.getCode(),apiException.getMessage());
        }
        ex.printStackTrace();
        logger.error("------------");
        logger.error(ex.getLocalizedMessage());

        return ApiResult.returnError("服务器内部错误");
    }

}
