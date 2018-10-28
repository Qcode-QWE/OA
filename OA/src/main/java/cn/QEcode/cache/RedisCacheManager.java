package cn.QEcode.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**  
* <p>Title: RedisCacheManager.java</p>  
* <p>Description:自定义缓存管理器 </p>   
* @author QEcode  
* @date 2018年10月27日  
* @version 1.0  
*/ 
public class RedisCacheManager implements CacheManager  {

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisCache<K,V>();
    }
    
}
