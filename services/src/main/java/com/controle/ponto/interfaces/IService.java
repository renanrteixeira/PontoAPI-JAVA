package com.controle.ponto.interfaces;

import java.util.List;

public interface IService<S, T>{
    List<T> findAll();
    T findById(String id);
    T post(S data);
    T put(S data);
}
