package com.denisbondd111.hashservice.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Objects;


public class Hash {
    @Id
    private Long id;
    private String hash;
    @Column("is_used")
    private Boolean isUsed;
    @Column("created_at")
    private Timestamp createdAt;

    public Hash() {
    }

    public Hash(Long id, String hash, Boolean isUsed, Timestamp createdAt) {
        this.id = id;
        this.hash = hash;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Hash hash1 = (Hash) o;
        return Objects.equals(getId(), hash1.getId()) && Objects.equals(getHash(), hash1.getHash()) && Objects.equals(isUsed, hash1.isUsed) && Objects.equals(getCreatedAt(), hash1.getCreatedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHash(), isUsed, getCreatedAt());
    }
}
