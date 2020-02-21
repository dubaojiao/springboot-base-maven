package com.duke.constant;

/**
 * @Title 系统 常量
 * @ClassName SysConstant
 * @Author duke
 * @Date 2018/9/13
 */
public interface SysConstant {

    //存储用户信息的key
    public static final String USER_INFO_PRO = "USERINFO%";
    //存储用户token的key
    public static final String USER_TOKEN_PRO = "USERTOKEN%";
    //30天
    public static final   Integer expire7 = 60*60*24*30;
    //1天
    public static final   Integer expire1 = 60*60*24;


    /**
     * web
     */
    public final static  String WEB = "/web/";
    /**
     * 回调
     */
    public final static  String CALLBACK = "/callback/";
    /**
     * app
     */
    public final static  String APP = "/app/";

    public final static  String ERROR_500 = "服务器内部错误";

    public final static  String ERROR_PARAM = "请求参数有误";

    public final static String PLEASE_CHOOSE = "请选择";


    public final static String SUCCESS = "成功";

    public final static String C_SUCCESS = "操作成功";

    public final static String C_FAILURE = "操作失败";

    public final static String PHONE_NO_CODE = "请点击获取验证码";

    public final static String CODE_ERROR = "验证码输入错误";

    public final static String GO_TO_REGISTER = "请前往注册";
    //首次启动
    public final static String FIRST_BOOT = "NO";
}
