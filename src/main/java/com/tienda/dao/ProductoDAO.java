package com.tienda.dao;

public interface ProductoDAO<T> {
    void create(T entity);
    T findById(Integer id);
    void update(T entity);
}