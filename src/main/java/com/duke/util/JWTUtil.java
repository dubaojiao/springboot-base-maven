package com.duke.util;

import com.duke.entity.redis.LoginUser;
import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletRequest;

public class JWTUtil {

    public static <T> String getToken(LoginUser loginUser){
        return CryptoUtil.encode32(null,CryptoUtil.encode32(null,JSONUtil.toJson(loginUser)));
    }


    public static LoginUser getInfo(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");

        if(Strings.isBlank(token)){
            return null;
        }
        String str = CryptoUtil.decode32(null,CryptoUtil.decode32(null,token));
        if(Strings.isBlank(str)){
            return null;
        }
        return JSONUtil.toBean(str,LoginUser.class);
    }

}
