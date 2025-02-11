package com.denisbondd111.hashservice.service;

import com.denisbondd111.hashservice.repository.HashRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HashService {
    private HashRepository hashRepository;

    @Autowired
    public HashService(HashRepository hashRepository) {
        this.hashRepository = hashRepository;
    }


}
