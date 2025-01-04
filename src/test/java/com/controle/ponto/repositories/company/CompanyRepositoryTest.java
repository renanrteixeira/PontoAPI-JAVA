package com.controle.ponto.repositories.company;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Company sucessfully from DB")
    void findByName_Found() {
        String name = "Company teste";
        CompanyRequestDTO data = new CompanyRequestDTO(null, name, "teste de company", "75 1111-1111");
        this.createCompany(data);

        Optional<Company> company = Optional.ofNullable(companyRepository.findByName(name));

        assertThat(company.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Company sucessfully from DB")
    void findByName_NotFound() {
        String name = "Company teste";
        Optional<Company> company = Optional.ofNullable(companyRepository.findByName(name));

        assertThat(company.isEmpty()).isTrue();
    }

    private Company createCompany(CompanyRequestDTO data){
        Company company = new Company(data);
        this.entityManager.persist(company);

        return company;
    }
}