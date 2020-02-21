package com.duke.component;

import com.duke.util.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Title redis 操作组件
 * @ClassName RedisComponent
 * @Author duke
 * @Date 2020/02/20
 */
@Component
public class RedisComponent {
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;


    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public String get(final String key){
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    public boolean del(String key) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(serializer.serialize(key));
                return true;
            }
        });
        return result;
    }

    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = JSONUtil.toJson(list);
        return set(key,value);
    }

    public <T> List<T> getList(String key,Class<T> clz) {
        String json = get(key);
        if(json!=null){
            List<T> list = JSONUtil.toList(json, clz);
            return list;
        }
        return null;
    }

    public long lpush(final String key, Object obj) {
        final String value;
        if (obj instanceof String) {
            value = obj.toString();
        } else {
            value = JSONUtil.toJson(obj);
        }
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    public long rpush(final String key, Object obj) {
        final String value;
        if (obj instanceof String) {
            value = obj.toString();
        } else {
            value = JSONUtil.toJson(obj);
        }
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res =  connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }

    public <T> List<T> range(String key, long start, long end) {
        try {
            ListOperations<String, ?> opts = redisTemplate.opsForList();
            return (List<T>) opts.range(key, start, end);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean removeListItem(String key, Object value, long count) {
        try {
            ListOperations<String, ?> opts = redisTemplate.opsForList();
            return opts.remove(key, count, value) >= count;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
