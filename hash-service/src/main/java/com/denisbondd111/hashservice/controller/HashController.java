package com.denisbondd111.hashservice.controller;

import com.denisbondd111.hashservice.dto.Text;
import com.denisbondd111.hashservice.entity.Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HashController {

    @GetMapping("hash")
    public Hash getHash(@RequestBody Text){

    }
}
