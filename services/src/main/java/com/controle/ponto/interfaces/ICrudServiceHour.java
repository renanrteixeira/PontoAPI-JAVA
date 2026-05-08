package com.controle.ponto.interfaces;

import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ICrudServiceHour<S, T> extends ICrudService<S, T> {
    void delete(String id);
    List<T> findByEmployee(@PathVariable String id);
}
