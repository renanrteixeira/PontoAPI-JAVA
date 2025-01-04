package com.controle.ponto.interfaces.services;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IServiceHour<S, T> extends IService<S, T>{
    void delete(String id);
    List<T> findByEmployee(@PathVariable String id);
}
