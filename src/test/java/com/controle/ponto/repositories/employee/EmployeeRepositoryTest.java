package com.controle.ponto.repositories.employee;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.role.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Employee sucessfully from DB")
    void findByName_Found() {
        String name = "Fulano";
        Date date = new Date();
        EmployeeRequestDTO data = new EmployeeRequestDTO(null, name, date, 'M', 'A',  "123", "123");

        this.createEmployee(data);

        Optional<Employee> result = Optional.ofNullable(employeeRepository.findByName(name));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Employee sucessfully from DB")
    void findByName_NotFound() {
        String name = "Fulano";
        Optional<Employee> result = Optional.ofNullable(employeeRepository.findByName(name));

        assertThat(result.isEmpty()).isTrue();
    }

    private Role createRole(RoleRequestDTO data){
        Role role = new Role(data);
        this.entityManager.persist(role);

        return role;
    }

    private Company createCompany(CompanyRequestDTO data){
        Company company = new Company(data);
        this.entityManager.persist(company);

        return company;
    }

    private Employee createEmployee(EmployeeRequestDTO data){
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(null, "role");
        var role = this.createRole(roleRequestDTO);

        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO(null, "company", "address", "11 1111-1111");
        var company = this.createCompany(companyRequestDTO);

        Employee employee = new Employee(data, role, company);
        this.entityManager.persist(employee);

        return employee;
    }

}