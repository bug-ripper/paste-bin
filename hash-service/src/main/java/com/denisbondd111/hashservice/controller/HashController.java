package com.denisbondd111.hashservice.controller;

import com.denisbondd111.hashservice.dto.HashDTO;
import com.denisbondd111.hashservice.dto.TextDTO;
import com.denisbondd111.hashservice.entity.Hash;
import com.denisbondd111.hashservice.service.HashProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class HashController {

    private final HashProviderService hashProviderService;

    @Autowired
    public HashController(HashProviderService hashProviderService) {
        this.hashProviderService = hashProviderService;
    }


    @GetMapping("hash")
    public ResponseEntity<HashDTO> getHash(){
        return new ResponseEntity<HashDTO>(hashProviderService.getHash(), HttpStatusCode.valueOf(200));
    }
}
