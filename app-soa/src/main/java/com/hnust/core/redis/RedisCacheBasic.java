package com.hnust.core.redis;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisCacheBasic {
    public void set(final String key, final String value, final RedisTemplate<Serializable, Serializable> redisTemplate, Integer expireSecs) {
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    if ("" != value && null != value && !"null".equals(value)) {
                        byte[] sk = redisTemplate.getStringSerializer().serialize(key);
                        connection.set(sk, redisTemplate.getStringSerializer().serialize(value));
                        connection.expire(sk, expireSecs);
                        return null;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception arg4) {
            arg4.printStackTrace();
        }        
    }
    
    public String get(final String key, final RedisTemplate<Serializable, Serializable> redisTemplate) {
        try {
            return (String)redisTemplate.execute(new RedisCallback<Object>() {
                public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    byte[] sk = redisTemplate.getStringSerializer().serialize(key);
                    if (connection.exists(sk).booleanValue()) {
                        byte[] sv = connection.get(sk);
                        return (String)redisTemplate.getStringSerializer().deserialize(sv);
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception arg3) {
            arg3.printStackTrace();
            return null;
        }
    }
    
    public void delete(final String key, final RedisTemplate<Serializable, Serializable> redisTemplate) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.del(new byte[][] {redisTemplate.getStringSerializer().serialize(key)});
                return null;
            }
        });
    }
    
    public void deleteByTag(final String key, final RedisTemplate<Serializable, Serializable> redisTemplate) {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                Set<byte[]> keys = connection.keys(redisTemplate.getStringSerializer().serialize(key + "*"));
                Iterator<byte[]> arg2 = keys.iterator();
                
                while (arg2.hasNext()) {
                    byte[] k = (byte[])arg2.next();
                    connection.del(new byte[][] {k});
                }
                
                return null;
            }
        });
    }
}