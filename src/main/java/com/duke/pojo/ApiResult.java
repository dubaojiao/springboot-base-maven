package com.duke.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.duke.util.CheckUtil;

import java.io.Serializable;

/**
 *  接口统一返回
 * Author: 杜报胶
 * Date: 2018-09-13
 */
public class ApiResult implements Serializable{

    /** 结果码
     * 100 - 需重试
     * 200 - 成功
     * 401 - 需要登录
     * 500 - 失败/错误
     * */
    private int code = 500;

    /** 消息 */
    private String message = "";

    /** 数据 */
    private Object data;

    @JSONField(serialize = false)
    private Exception ex;
    /** 是否成功 */
    @JSONField(serialize = false)
    private boolean success;

    public boolean isSuccess() {
        return success || 200 == this.code;
    }

    public int getCode() {
        return code;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        if (message != null) {
            this.message = message;
        }
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setSuccess(String message, Object data) {
        this.code    = 200;
        this.message = message;
        this.data    = data;
        this.success = true;
    }

    public void setSuccess(String message) {
        setSuccess(message, null);
    }

    public void setError(String message) {
        this.code    = 500;
        this.message = message;
        this.data    = null;
        this.success = false;
    }

    public void setAuthorization() {
        this.code    = 401;
        this.message = "登录过期，请重新登录";
        this.data    = null;
        this.success = false;
    }

    public void setProcessing(String message) {
        this.code    = 100;
        this.message = CheckUtil.stringIsNull(message)? "处理中，请稍后重试" : message;
        this.data    = null;
        this.success = false;
    }

    public static ApiResult returnSuccess(String message, Object data) {
        ApiResult result = new ApiResult();
        result.code    = 200;
        result.message = message;
        result.data    = data;
        result.success = true;
        return result;
    }

    public static ApiResult returnSuccess(String message) {
        return returnSuccess(message, null);
    }

    public static ApiResult returnError(String message) {
        ApiResult result = new ApiResult();
        result.code    = 500;
        result.message = message;
        return result;
    }

    public static ApiResult returnByCode(int code,String message) {
         if(code == 401){
            return returnAuthorization();
        }else {
            return returnError(message);
        }
    }


    public static ApiResult returnError(String message,Exception ex) {
        ApiResult result = new ApiResult();
        result.code    = 500;
        result.message = message;
        result.ex = ex;
        return result;
    }
    /**
     * 返回需要登录
     * @return 结果
     */
    public static ApiResult returnAuthorization()
    {
        ApiResult result = new ApiResult();
        result.setAuthorization();
        return result;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", ex=" + ex +
                ", success=" + success +
                '}';
    }
}
