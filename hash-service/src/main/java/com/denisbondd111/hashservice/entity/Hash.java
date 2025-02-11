package com.denisbondd111.hashservice.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("hash")
@Data
public class Hash {
    @Id
    private Long id;
    @Column("hash")
    private String hash;
}
