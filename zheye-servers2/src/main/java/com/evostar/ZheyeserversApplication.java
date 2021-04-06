package com.evostar;

import com.evostar.netty.config.NettyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(NettyProperties.class)
public class ZheyeserversApplication {

    @Bean
    public EhCacheCacheManager ehCacheCacheManager(){
        return new EhCacheCacheManager();
    }
    public static void main(String[] args) {
        SpringApplication.run(ZheyeserversApplication.class, args);
    }

}
