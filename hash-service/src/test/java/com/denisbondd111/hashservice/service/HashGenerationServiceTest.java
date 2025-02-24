package com.denisbondd111.hashservice.service;

import com.denisbondd111.hashservice.entity.Hash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashGenerationServiceTest {

    @Autowired
    private HashGenerationService hashGenerationService;

    @Test
    void generateHash() {
        Hash hash1 = hashGenerationService.generateHash(84L);
        Hash hash2 = hashGenerationService.generateHash(85L);
        assertEquals(hash1, hash2);
    }
}