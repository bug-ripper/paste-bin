package com.denisbondd111.hashservice.repository;

import com.denisbondd111.hashservice.entity.Hash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashRepository extends CrudRepository<Hash, Long> {
}
