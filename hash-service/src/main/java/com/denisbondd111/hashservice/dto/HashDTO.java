package com.denisbondd111.hashservice.dto;

import lombok.*;
import org.springframework.stereotype.Component;


public class HashDTO {
    private String hash;

    public HashDTO() {
    }

    public HashDTO(String hash) {
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
