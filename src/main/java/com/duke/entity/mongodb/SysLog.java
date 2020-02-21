package com.duke.entity.mongodb;

import com.duke.util.TimeUtils;

import java.io.Serializable;

/**
 * @Title 系统日志类
 * @ClassName SysLog
 * @Author duke
 * @Date 2018/9/13
 */
public class SysLog implements Serializable {

    /**
     * 主键.
     */
    private String logId;

    private Integer type;

    private String typeName;

    /**
     * 创建时间.
     */
    private String time;

    private String param;

    private String returnData;


    private Integer uid;

    private Long costMs;

    private String path;

    private String methodName;

    private String requestMethod;


    private String errorMsg;

    private String stackTrace;
    //端口
    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getCostMs() {
        return costMs;
    }

    public void setCostMs(Long costMs) {
        this.costMs = costMs;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time == null || "".equals(time)? TimeUtils.getCompleteDate():time;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
