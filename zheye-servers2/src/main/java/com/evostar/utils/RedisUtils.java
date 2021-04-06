package com.evostar.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void removeSetMember(String key, String value) {
        Boolean exist = redisTemplate.boundSetOps(key).isMember(value);
        if(exist){
            redisTemplate.boundSetOps(key).remove(value);
        }
    }

    public void increment(String key, int num){
        redisTemplate.opsForValue().increment(key, num);
    }
    public void clearNum(String key){
        redisTemplate.opsForValue().set(key, "0");
    }

    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
