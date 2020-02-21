package com.duke.component;

import com.duke.constant.RedisConstant;
import com.duke.constant.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 系统数据的缓存组件
 */
@Component
public class SysCacheComponent {

    private final RedisComponent redisComponent;

    @Autowired
    public SysCacheComponent(RedisComponent redisComponent){
        this.redisComponent = redisComponent;
    }

    /**
     * 缓存首次启动标识
     * @return
     * @throws Exception
     */
    public boolean cacheFirstBoot() throws Exception{
        try {
            String key = RedisConstant.getFirstBootKey();
            return redisComponent.set(key, SysConstant.FIRST_BOOT);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    /**
     *  获取首次启动标识
     * @return
     * @throws Exception
     */
    public String getFirstBoot() throws Exception{
        try {
            String key = RedisConstant.getFirstBootKey();
            return redisComponent.get(key);
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }
}
