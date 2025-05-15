package com.controle.ponto.persistence.hour;

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
import com.controle.ponto.persistence.employee.EmployeeRepository;
import com.controle.ponto.persistence.typedate.TypeDateRepository;
import com.controle.ponto.resources.utils.Date;
import com.controle.ponto.webapp.PontoApplication;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = PontoApplication.class)
@Transactional
class HourRepositoryTest {

    @Autowired
    HourRepository hourRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TypeDateRepository typeDateRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get Hours of Employee by id from DB")
    void findByEmployeeId_Found() {
        var employee = CreateEmployee();
        var typeDate = createTypeDate();

        java.util.Date date = new java.util.Date();
        LocalTime localTime = LocalTime.of(1,0,0);
        HourRequestDTO data = new HourRequestDTO(null, employee.getId(), date, typeDate.getId(), localTime, localTime, localTime, localTime,
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
        var employee = CreateEmployee();
        var typeDate = createTypeDate();

        java.util.Date date = new java.util.Date();
        LocalTime localTime = LocalTime.of(1,0,0);
        HourRequestDTO data = new HourRequestDTO(null, employee.getId(), date, typeDate.getId(), localTime, localTime, localTime, localTime,
                localTime, localTime);

        var newHour = this.createHour(data);
        var searchDate = Date.formatDate(newHour.getDate(), "YYYY-MM-dd");
        Optional<List<Hour>> result = Optional.ofNullable(hourRepository.findByEmployeeIdDate(newHour.getEmployee().getId(), searchDate));

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get Hours of Employee by id and date from DB")
    void findByEmployeeIdDate_NotFound() {
        String id = "1231231";
        java.util.Date date = new java.util.Date();
        var searchDate = Date.formatDate(date, "YYYY-MM-dd");
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
        RoleRequestDTO roleRequestDTO = new RoleRequestDTO(null, "com/controle/ponto/persistence/role");
        var role = this.createRole(roleRequestDTO);

        CompanyRequestDTO companyRequestDTO = new CompanyRequestDTO(null, "com/controle/ponto/persistence/company", "address", "11 1111-1111");
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

    private Optional<Employee> employeeFindById(String id){
        return employeeRepository.findById(id);
    }

    private Optional<TypeDate> typeDateFindById(String id){
        return typeDateRepository.findById(id);
    }

    private Hour createHour(HourRequestDTO data){
        var employee = employeeFindById(data.getEmployeeId());
        var typeDate = typeDateRepository.findById(data.getTypeDateId());

        Hour hour = new Hour(data, employee.get(), typeDate.get(), LocalTime.of(5,0,0));
        this.entityManager.persist(hour);

        return hour;
    }

    private TypeDate createTypeDate() {
        TypeDateRequestDTO typeDateRequestDTO = new TypeDateRequestDTO(null, "teste", LocalTime.of(1,0), 'S');
        var typeDate = this.createTypeDate(typeDateRequestDTO);
        return typeDate;
    }

    private Employee CreateEmployee() {
        java.util.Date date = new java.util.Date();
        EmployeeRequestDTO employeeRequestDTO = new EmployeeRequestDTO(null, "Fulano", date, 'M', 'A',  "123", "123");
        var employee = createEmployee(employeeRequestDTO);
        return employee;
    }

}