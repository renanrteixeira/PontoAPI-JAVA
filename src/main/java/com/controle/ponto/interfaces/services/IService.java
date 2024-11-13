package com.controle.ponto.interfaces.services;

import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface IService<S, T>{
    List<T> findAll();
    T findById(String id);
    T post(S data);
    T put(S data);
}
