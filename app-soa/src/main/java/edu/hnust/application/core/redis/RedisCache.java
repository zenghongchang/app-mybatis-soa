package edu.hnust.application.core.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCache extends RedisCacheBasic {
    
    @Value("${redis.expiration}")
    private Integer expireSecs;
    
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    
    public void set(String key, String value) {
        this.set(key, value, this.redisTemplate, expireSecs);
    }
    
    public String get(String key) {
        return this.get(key, this.redisTemplate);
    }
    
    public void delete(String key) {
        this.delete(key, this.redisTemplate);
    }
    
    public void deleteByTag(String key) {
        this.deleteByTag(key, this.redisTemplate);
    }
    
    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public void setExpireSecs(Integer expireSecs) {
        this.expireSecs = expireSecs;
    }
    
    public int getExpireSecs() {
        return expireSecs;
    }
    
    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }    
}