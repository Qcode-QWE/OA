 package cn.QEcode.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import redis.clients.jedis.Jedis;

/**  
* <p>Title: RedisCache.java</p>  
* <p>Description:自定义shiro的Redis缓存 </p>   
* @author Qcode  
* @date 2018年10月27日  
* @version 1.0  
*/ 

public class RedisCache<K,V> implements Cache<K,V> {

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    private String keyPrefix = "shiro_redis_session:";
    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        }else{
            return ByteSourceUtils.serialize((Serializable) key);
        }
    }


    @Override
    public Object get(Object key) throws CacheException {

        byte[] bytes = getByteKey(key);
        Jedis jedis = JedisUtils.getJedis();
        byte[] value = jedis.get(bytes);
        if(value == null){
            return null;
        }
        JedisUtils.close(jedis);
        return ByteSourceUtils.deserialize(value);
    }

    /**
     * 将shiro的缓存保存到redis中
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {

        Jedis jedis = JedisUtils.getJedis();
        jedis.set(getByteKey(key), ByteSourceUtils.serialize((Serializable)value));
        byte[] bytes = jedis.get(getByteKey(key));
        Object object = ByteSourceUtils.deserialize(bytes);
        JedisUtils.close(jedis);
        return object;

    }

    @Override
    public Object remove(Object key) throws CacheException {
        Jedis jedis = JedisUtils.getJedis();
        byte[] bytes = jedis.get(getByteKey(key));
        jedis.del(getByteKey(key));
        JedisUtils.close(jedis);
        return ByteSourceUtils.deserialize(bytes);
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
	Jedis jedis = JedisUtils.getJedis();
	jedis.flushDB();
	JedisUtils.close(jedis);
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
	Jedis jedis = JedisUtils.getJedis();
        Long size = jedis.dbSize();
        JedisUtils.close(jedis);
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
	Jedis jedis = JedisUtils.getJedis();
        Set<byte[]> keys = jedis.keys(new String("*").getBytes());
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(ByteSourceUtils.deserialize(bs));
        }
        JedisUtils.close(jedis);
        return set;
    }


    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        Jedis jedis = JedisUtils.getJedis();
        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            byte[] bytes = jedis.get(getByteKey(key));
            values.add(ByteSourceUtils.deserialize(bytes));
        }
        JedisUtils.close(jedis);
        return values;
    }
}
