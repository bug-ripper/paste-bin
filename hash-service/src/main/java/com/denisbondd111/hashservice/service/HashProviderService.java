package com.denisbondd111.hashservice.service;

import com.denisbondd111.hashservice.dto.HashDTO;
import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.repository.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

@Service
public class HashProviderService {

    private HashRepository hashRepository;
    private RedisOperations<String, Hash> hashRedisTemplate;

    @Autowired
    public HashProviderService(HashRepository hashRepository, RedisOperations<String, Hash> hashRedisTemplate) {
        this.hashRepository = hashRepository;
        this.hashRedisTemplate = hashRedisTemplate;
    }

    public HashDTO getHash(){
        if (hashRedisTemplate.opsForList().size("hashes") != 0){
            Hash hash = hashRedisTemplate.opsForList().leftPop("hashes");
            hash.setUsed(true);
            hashRedisTemplate.opsForList().rightPush("used_hashes", hash);
            return new HashDTO(hash.getHash());
        }else {
            return new HashDTO(hashRepository.getFirstNotUsedHash());
        }
    }
}
