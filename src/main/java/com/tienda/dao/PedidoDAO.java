package com.tienda.dao;

import java.util.List;

public interface PedidoDAO<T> {
    void create(T entity);
    List<T> findAll();
}