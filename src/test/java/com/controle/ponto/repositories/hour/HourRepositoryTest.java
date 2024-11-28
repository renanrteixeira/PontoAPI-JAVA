package com.controle.ponto.repositories.hour;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.hour.Hour;
import com.controle.ponto.domain.role.Role;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.utils.Utils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class HourRepositoryTest {

    @Autowired
    HourRepository hourRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Hours of Employee by id from DB")
    void findByEmployeeId_Found() {
        Date date = new Date();
        LocalTime localTime = LocalTime.of(1,0,0);
        HourRequestDTO data = new HourRequestDTO(null, "123", date, "123", localTime, localTime, localTime, localTime,
                localTime, localTime);

        var newHour = this.createHour(data);
        Optional<List<Hour>> result = Optional.ofNullable(hourRepository.findByEmployeeId(newHour.getEmployee().getId()));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get Hours of Employee by id from DB")
    void findByEmployeeId_NotFound() {
        String id = "1231231";
        Optional<List<Hour>> result = Optional.ofNullable(hourRepository.findByEmployeeId(id));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get Hours of Employee by id and date from DB")
    void findByEmployeeIdDate_Found() {
        Date date = new Date();
        LocalTime localTime = LocalTime.of(1,0,0);
        HourRequestDTO data = new HourRequestDTO(null, "123", date, "123", localTime, localTime, localTime, localTime,
                localTime, localTime);

        var newHour = this.createHour(data);
        var searchDate = Utils.formatDate(newHour.getDate(), "YYYY-MM-dd");
        Optional<List<Hour>> result = Optional.ofNullable(hourRepository.findByEmployeeIdDate(newHour.getEmployee().getId(), searchDate));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get Hours of Employee by id and date from DB")
    void findByEmployeeIdDate_NotFound() {
        String id = "1231231";
        Date date = new Date();
        var searchDate = Utils.formatDate(date, "YYYY-MM-dd");
        Optional<List<Hour>> result = Optional.ofNullable(hourRepository.findByEmployeeIdDate(id, searchDate));

        assertThat(result.isPresent()).isTrue();
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

    private TypeDate createTypeDate(TypeDateRequestDTO data){
        TypeDate typeDate = new TypeDate(data);
        this.entityManager.persist(typeDate);

        return typeDate;
    }

    private Hour createHour(HourRequestDTO data){
        Date date = new Date();
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO(null, "Fulano", date, 'M', 'A',  "123", "123");
        var employee = createEmployee(employeeRequestDTO);

        TypeDateRequestDTO typeDateRequestDTO = new TypeDateRequestDTO(null, "teste", LocalTime.of(1,0), 'S');
        var typeDate = this.createTypeDate(typeDateRequestDTO);

        Hour hour = new Hour(data, employee, typeDate, LocalTime.of(5,0,0));
        this.entityManager.persist(hour);

        return hour;
    }

}