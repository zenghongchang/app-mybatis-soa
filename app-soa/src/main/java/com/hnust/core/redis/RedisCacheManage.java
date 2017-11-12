package com.hnust.core.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisCacheManage implements Cache {
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;    
    @Value("default")
    private String name;    
    @Value("${redis.expiration}")
    private Integer liveTime;
    
    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }
    
    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Object getNativeCache() {
        return this.redisTemplate;
    }
    
    @Override
    public ValueWrapper get(Object key) {        
        try {
            final String keyf = obj2Str(key);
            Object object = null;
            object = redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {                    
                    byte[] key = keyf.getBytes();
                    byte[] value = connection.get(key);
                    if (value == null) {
                        return null;
                    }
                    return toObject(value);
                    
                }
            });
            return (object != null ? new SimpleValueWrapper(object) : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void put(Object key, Object value) {        
        try {
            final String keyf = obj2Str(key);
            final Object valuef = value;
            
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    byte[] keyb = keyf.getBytes();
                    byte[] valueb = toByteArray(valuef);
                    connection.set(keyb, valueb);
                    if (liveTime > 0) {
                        connection.expire(keyb, liveTime);
                    }
                    return 1L;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void put(Object key, Object value, Integer liveTime) {
        try {
            final String keyf = obj2Str(key);
            final Object valuef = value;
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    byte[] keyb = keyf.getBytes();
                    byte[] valueb = toByteArray(valuef);
                    connection.set(keyb, valueb);
                    if (liveTime > 0) {
                        connection.expire(keyb, liveTime);
                    }
                    return 1L;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String obj2Str(Object key) {
        String keyStr = null;
        if (key instanceof Integer) {
            keyStr = ((Integer)key).toString();
        } else if (key instanceof Long) {
            keyStr = ((Long)key).toString();
        } else {
            keyStr = (String)key;
        }
        return keyStr;
    }
    
    /**
     * 描述 : <Object转byte[]>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     * 
     * @param obj
     * @return
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
    
    /**
     * 描述 : <byte[]转Object>. <br>
     * <p>
     * <使用方法说明>
     * </p>
     * 
     * @param bytes
     * @return
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
    
    @Override
    public void evict(Object key) {
        try {
            final String keyf = obj2Str(key);
            redisTemplate.execute(new RedisCallback<Long>() {
                public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    return connection.del(keyf.getBytes());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteByTag(final String key) {
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                public Long doInRedis(RedisConnection connection) {
                    Set<byte[]> keys = connection.keys(redisTemplate.getStringSerializer().serialize(key + "*"));// 87
                    Iterator<byte[]> arg3 = keys.iterator();
                    while (arg3.hasNext()) {
                        byte[] k = (byte[])arg3.next();
                        connection.del(new byte[][] {k});
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void clear() {
        try {
            redisTemplate.execute(new RedisCallback<String>() {
                public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                    connection.flushDb();
                    return "ok";
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        ValueWrapper wrapper = get(key);
        return wrapper == null ? null : (T)wrapper.get();
    }
    
    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        synchronized (key) {
            ValueWrapper wrapper = get(key);
            if (wrapper != null) {
                return wrapper;
            }
            put(key, value);
            return toWrapper(value);
        }
    }
    
    private ValueWrapper toWrapper(Object value) {
        return (value != null ? new SimpleValueWrapper(value) : null);
    }
    
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }
    
    public void setLiveTime(Integer liveTime) {
        this.liveTime = liveTime;
    }
    
    public Integer getLiveTime() {
        return liveTime;
    }    
}