package com.denisbondd111.hashservice.init;

import com.denisbondd111.hashservice.daemon.HashBackgroundProcessor;
import com.denisbondd111.hashservice.entity.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;

@Component
public class ApplicationInitializer {

    private final HashBackgroundProcessor fillHashesDaemon;
    private RedisOperations<String, Hash> hashRedisTemplate;

    @Autowired
    public ApplicationInitializer(HashBackgroundProcessor fillDatabaseDaemon, RedisOperations<String, Hash> hashRedisTemplate) {
        this.fillHashesDaemon = fillDatabaseDaemon;
        this.hashRedisTemplate = hashRedisTemplate;
    }

    private void cleanRedis(){
    }

    @Order(1)
    @EventListener(ApplicationStartedEvent.class)
    private void init(){
        fillHashesDaemon.fillDatabase();
        fillHashesDaemon.fillCache();
    }
}
