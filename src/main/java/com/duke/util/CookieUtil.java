package com.duke.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie的工具类
 */
public class CookieUtil {
    /*
     cookie名字
      */
    private static final String KEY = "key";

    public static String getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return null;
        }
        for(Cookie cookie: cookies){
            if(KEY.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 设置cookie
     * @param response
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String value, int maxAge){
        Cookie cookie = new Cookie(KEY,value);
        cookie.setPath("/");
        if(maxAge>0)  {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 更新cookie的过期时间
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void updateCookieTime(HttpServletRequest request, int maxAge){
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0){
            return;
        }
        for(Cookie cookie: cookies){
            if(KEY.equals(cookie.getName())){
                if(maxAge>0)  cookie.setMaxAge(maxAge);
                return;
            }
        }
        return;
    }

}
