package com.duke.service;

import com.duke.component.UserCacheComponent;
import com.duke.dao.UserDao;
import com.duke.entity.mysql.User;
import com.duke.entity.redis.LoginUser;
import com.duke.exception.ApiException;
import com.duke.pojo.ApiResult;
import com.duke.pojo.LoginData;
import com.duke.util.CheckUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final UserDao userDao;
    private final UserCacheComponent userCacheComponent;

    public UserService(UserDao userDao,UserCacheComponent userCacheComponent){
        this.userCacheComponent = userCacheComponent;
        this.userDao = userDao;
    }

    public String login(LoginData data) throws Exception{
        User user = userDao.findByPhone(data.getPhone());
        if(CheckUtil.objectIsNull(user)){
            throw new ApiException(500,"账号不存在");
        }
        if(!user.getPwd().trim().toUpperCase().equals(data.getPwd().trim().toUpperCase())){
            throw new ApiException(500,"账号或者密码错误");
        }
        LoginUser loginUser = new LoginUser();

        loginUser.setAppType("");
        loginUser.setLoginTime(new Date());
        loginUser.setNickname(user.getPhone());
        loginUser.setRoleId(0);
        loginUser.setRoleName("");
        loginUser.setUid(user.getId());
        loginUser.setUserPhone(user.getPhone());
        loginUser.setUserType(1);
        loginUser.setUserName(user.getPhone());

        String token = userCacheComponent.cacheUser(loginUser);

        if(CheckUtil.stringIsNull(token))throw new ApiException(500,"用户信息缓存失败");

        return token;
    }

    public String getUserInfo() throws Exception{
        LoginUser loginUser = userCacheComponent.getCacheUser();

        if(CheckUtil.objectIsNull(loginUser)){
            throw new ApiException(401,"未登录");
        }

        return loginUser.toString();
    }
}
