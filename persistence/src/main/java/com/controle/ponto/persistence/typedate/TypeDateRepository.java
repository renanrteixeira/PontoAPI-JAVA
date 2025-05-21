package com.controle.ponto.persistence.typedate;

import com.controle.ponto.domain.entity.typedate.TypeDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeDateRepository extends JpaRepository<TypeDate, String> {
    TypeDate findByName(String name);
}
