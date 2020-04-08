package com.duke.api;

import com.duke.pojo.LoginData;
import com.duke.service.UserService;
import com.duke.util.ParamVerification;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/web/user/")
public class UserController {

    private final UserService userService;

    public UserController (UserService userService){
        this.userService =  userService;
    }

    @PostMapping("login")
    public Object Login(@RequestBody LoginData data) throws Exception{
        //ParamVerification.valid(data);
        return userService.login(data);
    }


    @GetMapping(value = "get/info")
    public Object getUserInfo() throws Exception{
        return userService.getUserInfo();
    }
}
