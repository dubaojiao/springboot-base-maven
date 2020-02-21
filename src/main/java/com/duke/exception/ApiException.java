package com.duke.exception;

public class ApiException extends RuntimeException {

    private String message;

    private int code;

    public ApiException(int code,String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 重写堆栈填充，不填充错误堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
