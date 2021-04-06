package com.evostar.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;


@Component
public class EhcacheUtils {
    @Autowired
    private EhCacheCacheManager ehCacheCacheManager;

    public void put(String cacheName,String key,Object value){
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        Element element = new Element(key,value);
        cache.put(element);
    }

    public Object get(String cacheName,String key){
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        Element element = cache.get(key);
        if(element == null)
            return null;
        return element.getObjectValue();
    }

    public void clear(String cacheName,String key){
        Cache cache = ehCacheCacheManager.getCacheManager().getCache(cacheName);
        cache.remove(key);
    }
}
