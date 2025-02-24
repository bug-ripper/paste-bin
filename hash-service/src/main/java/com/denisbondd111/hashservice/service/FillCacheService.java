package com.denisbondd111.hashservice.service;

import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.repository.HashRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FillCacheService {

    private RedisOperations<String, Hash> hashRedisTemplate;

    public FillCacheService(RedisOperations<String, Hash> hashRedisTemplate) {
        this.hashRedisTemplate = hashRedisTemplate;
    }

    public void fill(List<Hash> hashes){
        hashRedisTemplate.opsForList().rightPushAll("hashes", hashes);
    }
}
