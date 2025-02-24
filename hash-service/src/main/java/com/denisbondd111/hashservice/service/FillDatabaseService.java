package com.denisbondd111.hashservice.service;


import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.repository.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class FillDatabaseService {
    private final HashRepository hashRepository;
    private HashGenerationService hashGenerationService;
    @Value("${application.hash.generation.pull-size:100}")
    private int pullSize;

    @Autowired
    public FillDatabaseService(HashRepository hashRepository, HashGenerationService hashGenerationService) {
        this.hashRepository = hashRepository;
        this.hashGenerationService = hashGenerationService;
    }

    public boolean fill(){
        hashRepository.saveAll(hashGenerationService.generateHash(pullSize));
        return true;
    }
}
