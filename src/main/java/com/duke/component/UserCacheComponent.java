package com.duke.component;

import com.duke.constant.RedisConstant;
import com.duke.constant.SysConstant;
import com.duke.entity.redis.LoginUser;
import com.duke.util.CheckUtil;
import com.duke.util.JSONUtil;
import com.duke.util.RandomCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.duke.constant.SysConstant.*;

/**
 * @Title 系统缓存 组件
 * @ClassName SysCacheComponent
 * @Author duke
 * @Date 2018/9/13
 */
@Component
public class UserCacheComponent {

    @Autowired(required = false)
    HttpServletResponse response;

    @Autowired(required = false)
    HttpServletRequest request;


   final RedisComponent redisComponent;

    private Map<String,UserCache> userCacheMap = new HashMap<>(2);


    @Autowired
    public UserCacheComponent(RedisComponent redisComponent){

        this.redisComponent = redisComponent;

        userCacheMap.put(SysConstant.APP,new AppUserCache(redisComponent));
        userCacheMap.put(SysConstant.WEB,new WebUserCache(redisComponent));
    }

    /**
     * 缓存用户信息
     * @param loginUser 登录用户对象
     * @return token
     */
    public String cacheUser(LoginUser loginUser){
        UserCache userCache = getUserCacheImpl();
        if (CheckUtil.objectIsNull(userCache)) {
            return null;
        }
        return userCache.cache(loginUser);
    }

    /**
     * 获取登录用户信息
     * @return
     */
    public LoginUser getCacheUser(){
        UserCache userCache = getUserCacheImpl();
        if (CheckUtil.objectIsNull(userCache)) {
            return null;
        }
        return userCache.get(request);
    }

    public void emptyCache(Integer uid){
        UserCache userCache = getUserCacheImpl();
        if (CheckUtil.objectIsNull(userCache)) {
            return;
        }
        userCache.empty(uid);
    }


    private String urlKey(String url){
        if(CheckUtil.stringIsNull(url)) return null;
        if(url.contains(SysConstant.WEB)){
            return SysConstant.WEB;
        }else if(url.contains(SysConstant.APP)){
            return SysConstant.APP;
        }else {
            return  null;
        }
    }

    private UserCache getUserCacheImpl(){
        if(null == request){
            return null;
        }
        String key = urlKey(request.getRequestURI());
        return userCacheMap.get(key);
    }
}

 interface UserCache{
    LoginUser get(HttpServletRequest request);

    void empty(Integer uid);

    String cache(LoginUser loginUser);
}
 class WebUserCache implements UserCache{

     RedisComponent redisComponent;

     public WebUserCache(RedisComponent redisComponent) {
         this.redisComponent = redisComponent;
     }

     @Override
     public LoginUser get(HttpServletRequest request) {
         try {
             String key = request.getHeader("token");
             if(CheckUtil.stringIsNull(key)){
                 return null;
             }
             String value = redisComponent.get(RedisConstant.getWebKey(key));
             if(CheckUtil.stringIsNull(value)){
                 return null;
             }
             String object = redisComponent.get(RedisConstant.getWebKey(value));
             if(CheckUtil.stringIsNull(object)){
                 return null;
             }
             //刷新缓存时间
             redisComponent.expire(RedisConstant.getWebKey(key),expire1);
             redisComponent.expire(RedisConstant.getWebKey(value),expire1);
             return  JSONUtil.toBean(object,LoginUser.class);
         }catch (Exception ex){
             ex.printStackTrace();
             return null;
         }
     }

     @Override
     public void empty(Integer uid) {
         try {
             String userInfoKey = RedisConstant.getWebKey(USER_INFO_PRO.replace("%",uid.toString()));
             String userTokenKey = RedisConstant.getWebKey(USER_TOKEN_PRO.replace("%",uid.toString()));
             String token = RedisConstant.getWebKey(redisComponent.get(userTokenKey));
             redisComponent.del(token);
             redisComponent.del(userInfoKey);
             redisComponent.del(userTokenKey);
         }catch (Exception ex){
             ex.printStackTrace();
         }
     }

     @Override
     public String cache(LoginUser user) {
         this.empty(user.getUid());
         try {
             String token = RandomCodeUtil.getUUID();
             String userInfoKey = USER_INFO_PRO.replace("%",user.getUid().toString());
             redisComponent.set(RedisConstant.getWebKey(token),userInfoKey);
             String userTokenKey = USER_TOKEN_PRO.replace("%",user.getUid().toString());
             redisComponent.set(RedisConstant.getWebKey(userTokenKey),token);
             redisComponent.set(RedisConstant.getWebKey(userInfoKey), JSONUtil.toJson(user));
             redisComponent.expire(RedisConstant.getWebKey(token),expire1);
             redisComponent.expire(RedisConstant.getWebKey(userTokenKey),expire1);
             return token;
         }catch (Exception ex){
             ex.printStackTrace();
         }
         return null;
     }
 }

 class AppUserCache implements UserCache{

     RedisComponent redisComponent;


     public AppUserCache(RedisComponent redisComponent) {
         this.redisComponent = redisComponent;
     }

     @Override
     public LoginUser get(HttpServletRequest request) {
         try {
             String key = request.getHeader("token");

             if(CheckUtil.stringIsNull(key)){
                 return null;
             }
             String value = redisComponent.get(RedisConstant.getAppKey(key));
             if(CheckUtil.stringIsNull(value)){
                 return null;
             }
             String object = redisComponent.get(RedisConstant.getAppKey(value));
             if(CheckUtil.stringIsNull(object)){
                 return null;
             }
             //刷新缓存时间
             redisComponent.expire(RedisConstant.getAppKey(key),expire7);
             redisComponent.expire(RedisConstant.getAppKey(value),expire7);
             return  JSONUtil.toBean(object,LoginUser.class);
         }catch (Exception ex){
             ex.printStackTrace();
             return null;
         }
     }

     @Override
     public void empty(Integer uid) {
         try {
             String userInfoKey = RedisConstant.getAppKey(USER_INFO_PRO.replace("%",uid.toString()));
             String userTokenKey = RedisConstant.getAppKey(USER_TOKEN_PRO.replace("%",uid.toString()));
             String token = RedisConstant.getAppKey(redisComponent.get(userTokenKey));
             redisComponent.del(token);
             redisComponent.del(userInfoKey);
             redisComponent.del(userTokenKey);
         }catch (Exception ex){
             ex.printStackTrace();
         }
     }

     @Override
     public String cache(LoginUser user) {
         this.empty(user.getUid());
         try {
             String token = RandomCodeUtil.getUUID();
             String userInfoKey = USER_INFO_PRO.replace("%",user.getUid().toString());
             redisComponent.set(RedisConstant.getAppKey(token),userInfoKey);
             String userTokenKey = USER_TOKEN_PRO.replace("%",user.getUid().toString());
             redisComponent.set(RedisConstant.getAppKey(userTokenKey),token);
             redisComponent.set(RedisConstant.getAppKey(userInfoKey), JSONUtil.toJson(user));

             redisComponent.expire(RedisConstant.getAppKey(token),SysConstant.expire7);
             redisComponent.expire(RedisConstant.getAppKey(userTokenKey),SysConstant.expire7);
             return token;
         }catch (Exception ex){
             ex.printStackTrace();
         }
         return null;
     }
 }