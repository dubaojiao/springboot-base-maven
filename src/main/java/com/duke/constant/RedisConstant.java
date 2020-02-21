package com.duke.constant;
/**
 * @Title redis 常量
 * @ClassName RedisConstant
 * @Author duke
 * @Date 2018/9/13
 */
public final  class RedisConstant {

    private final static  String BASE = "base";
    private final static  String WEB= "web";
    private final static  String APP= "app";
    private final static String CODE = "code";
    private final static String FIRST_BOOT = "firstBoot";

    public static  String getFirstBootKey(){
        return new StringBuilder(BASE).append(":").append(FIRST_BOOT).toString();
    }

    /**
     * 系统缓存分区
     */
    private final static String SYS_KEY = "sys";
    /**
     * 缓存菜单分区
     */
    private final static String SYS_MENU_KEY = "menu";

    public static  String getWebKey(String token){
        return new StringBuilder(BASE).append(":").append(WEB).append(":").append(token).toString();
    }


    public static  String getAppKey(String token){
        return new StringBuilder(BASE).append(":").append(APP).append(":").append(token).toString();
    }


    /**
     * 菜单缓存的key
     *
     * @param code
     * @return
     */
    public static final String getWebSysMenuKey(String code) {
        return new StringBuilder(BASE).append(":").append(WEB).append(":").append(SYS_KEY).append(":").append(SYS_MENU_KEY).append(":").append(code).toString();
    }

    public static String getAppCodeKey(String phone) {
        return new StringBuilder(BASE).append(":").append(APP).append(":").append(CODE).append(":").append(phone).toString();
    }
}
