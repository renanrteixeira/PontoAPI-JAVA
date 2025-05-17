package com.controle.ponto.persistence.company;

import com.controle.ponto.domain.entity.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByName(String name);
}
