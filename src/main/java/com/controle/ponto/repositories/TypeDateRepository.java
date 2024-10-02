package com.controle.ponto.repositories;

import com.controle.ponto.domain.typedate.TypeDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDateRepository extends JpaRepository<TypeDate, String> {
    TypeDate findByName(String name);
}
