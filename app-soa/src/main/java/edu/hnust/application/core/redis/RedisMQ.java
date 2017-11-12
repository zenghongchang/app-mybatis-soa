package edu.hnust.application.core.redis;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisMQ<T> {
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplateMQ;    
    private final Long MAX_DELAY_MICRO_SECS = Long.valueOf(1000L);
    
    public void send(final String key, final String value) {
        try {
            this.redisTemplateMQ.execute(new RedisCallback<T>() {
                public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                        byte[] sk = RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(key);
                        connection.sAdd(sk, new byte[][] {RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(value)});
                        return null;
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception arg3) {
            arg3.printStackTrace();
        }
        
    }
    
    public String receive(final String key) {
        try {
            return (String)this.redisTemplateMQ.execute(new RedisCallback<Object>() {
                public String doInRedis(RedisConnection arg0)
                    throws DataAccessException {
                    if (StringUtils.isEmpty(key)) {
                        return null;
                    } else {
                        byte[] arg1 = RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(key);
                        String arg2 = null;                        
                        while (true) {
                            byte[] arg3 = arg0.sPop(arg1);
                            arg2 = (String)RedisMQ.this.redisTemplateMQ.getStringSerializer().deserialize(arg3);
                            if (null != arg2) {
                                return arg2;
                            }                            
                            try {
                                Thread.sleep(RedisMQ.this.MAX_DELAY_MICRO_SECS.longValue());
                            } catch (Exception arg5) {
                                arg5.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (Exception arg2) {
            arg2.printStackTrace();
            return null;
        }
    }

    public Set<String> receive(final String key, int limit) {
        LinkedHashSet<String> results = new LinkedHashSet<String>();       
        try {
            while (limit-- > 0) {
                String e = (String)this.redisTemplateMQ.execute(new RedisCallback<Object>() {
                    public String doInRedis(RedisConnection connection)
                        throws DataAccessException {
                        if (StringUtils.isEmpty(key)) {
                            return null;
                        } else {
                            byte[] sk = RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(key);
                            byte[] sv = connection.sPop(sk);
                            return (String)RedisMQ.this.redisTemplateMQ.getStringSerializer().deserialize(sv);
                        }
                    }
                });
                if (null == e) {
                    break;
                }                
                results.add(e);
            }
        } catch (Exception arg4) {
            arg4.printStackTrace();
        }        
        return results;
    }
    
    public Object remove(final String key, final String value) {
        try {
            return this.redisTemplateMQ.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection) {
                    if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
                        byte[] sk = RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(key);
                        byte[] sv = RedisMQ.this.redisTemplateMQ.getStringSerializer().serialize(value);
                        return connection.sRem(sk, new byte[][] {sv});
                    } else {
                        return null;
                    }
                }
            });
        } catch (Exception arg3) {
            arg3.printStackTrace();
            return Integer.valueOf(0);
        }
    }
    
    public void setRedisTemplateMQ(RedisTemplate<Serializable, Serializable> redisTemplateMQ) {
        this.redisTemplateMQ = redisTemplateMQ;
    }
    
    public RedisTemplate<Serializable, Serializable> getRedisTemplateMQ() {
        return this.redisTemplateMQ;
    }
}