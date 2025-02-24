package com.denisbondd111.hashservice.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, I>{
    Optional<T> findById(I id);
    List<T> findAll();
    void save(T entity);
    void saveAll(List<T> entity);
    void update(T entity);
    void delete(T entity);
}
