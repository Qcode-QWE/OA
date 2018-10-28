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
        byte[] value = JedisUtils.getJedis().get(bytes);
        if(value == null){
            return null;
        }
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

        return object;

    }

    @Override
    public Object remove(Object key) throws CacheException {
        Jedis jedis = JedisUtils.getJedis();

        byte[] bytes = jedis.get(getByteKey(key));

        jedis.del(getByteKey(key));

        return ByteSourceUtils.deserialize(bytes);
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
	JedisUtils.getJedis().flushDB();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = JedisUtils.getJedis().dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = JedisUtils.getJedis().keys(new String("*").getBytes());
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(ByteSourceUtils.deserialize(bs));
        }
        return set;
    }


    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            byte[] bytes = JedisUtils.getJedis().get(getByteKey(key));
            values.add(ByteSourceUtils.deserialize(bytes));
        }
        return values;
    }
}
