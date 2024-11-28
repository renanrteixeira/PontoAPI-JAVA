package com.controle.ponto.repositories.employee;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.role.Role;
import com.controle.ponto.repositories.company.CompanyRepository;
import com.controle.ponto.repositories.role.RoleRepository;
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
    CompanyRepository companyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Employee sucessfully from DB")
    void findByName_Found() {
        String name = "Fulano";
        Date date = new Date();

        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(null, "role");
        var role = this.createRole(roleRequestDTO);

        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO(null, "company", "address", "11 1111-1111");
        var company = this.createCompany(companyRequestDTO);

        EmployeeRequestDTO data = new EmployeeRequestDTO(null, name, date, 'M', 'A',  role.getId(), company.getId());

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

    private Optional<Role> roleFindById(String id){
        return roleRepository.findById(id);
    }

    private Optional<Company> companyFindById(String id){
        return companyRepository.findById(id);
    }

    private Employee createEmployee(EmployeeRequestDTO data){
        var role = roleFindById(data.getRoleId());
        var company = companyFindById(data.getCompanyId());

        Employee employee = new Employee(data, role.get(), company.get());
        this.entityManager.persist(employee);

        return employee;
    }

}