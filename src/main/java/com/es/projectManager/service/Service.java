package com.es.projectManager.service;

import java.util.List;
import java.util.Optional;

public interface Service <T>{

    void saveOrUpdate(T entity);

    List<T> readAll();

    boolean delete(T entity);

    default Optional<T> read(Integer id){return Optional.empty();}
}
