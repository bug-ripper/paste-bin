package com.denisbondd111.hashservice.daemon;

import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.repository.HashRepository;
import com.denisbondd111.hashservice.service.FillCacheService;
import com.denisbondd111.hashservice.service.FillDatabaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class HashBackgroundProcessor {
    private RedisOperations<String, Hash> hashRedisTemplate;
    private final HashRepository hashRepository;
    private final FillDatabaseService fillDatabaseService;
    private final FillCacheService fillCacheService;
    @Value("${application.hash.generation.pull-size:50}")
    private int pullDbSize;
    @Value("${application.hash.cache.pull-size:10}")
    private int pullCacheSize;

    @Autowired
    public HashBackgroundProcessor(RedisOperations<String, Hash> hashRedisTemplate, HashRepository hashRepository, FillDatabaseService fillDatabaseService, FillCacheService fillCacheService) {
        this.hashRedisTemplate = hashRedisTemplate;
        this.hashRepository = hashRepository;
        this.fillDatabaseService = fillDatabaseService;
        this.fillCacheService = fillCacheService;
    }


    @Scheduled(fixedRate = 5)
    public void fillDatabase(){
        Long numberOfUnusedHashes = hashRepository.getNumberOfUnusedHashes();
        if (numberOfUnusedHashes < pullDbSize/4){
            fillDatabaseService.fill();
            log.info("DB are filled");
        }
    }

    @Scheduled(fixedRate = 5)
    public void fillCache(){
        if (hashRedisTemplate.opsForList().size("hashes") < pullCacheSize/4){
            fillCacheService.fill(hashRepository.getUnusedHashes(pullCacheSize));
            log.info("Cache are filled");
        }
    }

    @Scheduled(fixedRate = 5)
    public void writeUsedHashes(){
        Long sizeUnusedHashesList = hashRedisTemplate.opsForList().size("used_hashes");
        if (sizeUnusedHashesList > 0L){
//            List<Hash> usedHashes = new ArrayList<>();
            for(int i = 0; i < sizeUnusedHashesList; i++){
//                usedHashes.add(hashRedisTemplate.opsForList().leftPop("used_hashes"));
                Hash usedHash = hashRedisTemplate.opsForList().leftPop("used_hashes");
                usedHash.setUsed(Boolean.TRUE);
                hashRepository.update(usedHash);
            }
//            hashRepository.updateAll(usedHashes);
            log.info("Used hashes are wrote");
        }
    }
}
