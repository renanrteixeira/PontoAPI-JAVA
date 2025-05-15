package com.controle.ponto.persistence.company;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.webapp.PontoApplication;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = PontoApplication.class)
@Transactional
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