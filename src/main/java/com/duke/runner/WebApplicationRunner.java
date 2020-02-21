package com.duke.runner;

import com.duke.component.SysCacheComponent;
import com.duke.constant.SysConstant;
import com.duke.dao.UserDao;
import com.duke.entity.mysql.User;
import com.duke.util.CheckUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * @Title 项目启动-最后执行的类
 * @ClassName WebApplicationRunner
 * @Author duke
 * @Date 2018/9/13
 */

@Component
@Order(value = 999)
public class WebApplicationRunner implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(WebApplicationRunner.class);

    private final UserDao userDao;

    private final SysCacheComponent sysCacheComponent;

    @Autowired
    public WebApplicationRunner(UserDao userDao,SysCacheComponent sysCacheComponent){
        this.userDao = userDao;
        this.sysCacheComponent = sysCacheComponent;
    }




    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("项目启动检测初始化数据");
        String flag = sysCacheComponent.getFirstBoot();
        if (!CheckUtil.stringIsNull(flag) && SysConstant.FIRST_BOOT.equals(flag)) {
            // 不是首次启动直接过；
            logger.info("无需初始化：项目启动完成");
            return;
        }
        // 是首次启动
        userDao.deleteAll();

        //条件系统默认用户
        User user = new User();
        user.setId(1);
        user.setPhone("18888888888");
        user.setTime(new Date());
        user.setPwd("123456");

        userDao.save(user);

        logger.info("初始化：项目启动完成");
    }
}
