package com.controle.ponto.services.employee;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.employee.Employee;
import com.controle.ponto.domain.role.Role;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.repositories.company.CompanyRepository;
import com.controle.ponto.repositories.employee.EmployeeRepository;
import com.controle.ponto.repositories.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CompanyRepository companyRepository;

    public List<EmployeeResponseDTO> findAll(){
        var employees = employeeRepository.findAll();

        List<EmployeeResponseDTO> listEmployee = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeResponseDTO empl = new EmployeeResponseDTO(employee);
            listEmployee.add(empl);
        }

        return listEmployee;
    }

    public EmployeeResponseDTO findById(String id){
        Optional<Employee> employee = getOptionalEmployeeFindById(id);
        Employee foundEmployee = employee.get();

        return  new EmployeeResponseDTO(foundEmployee);

    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data){
        VerifyEmployeeFindByName(data);
        Optional<Role> role = getOptionalRole(data);
        Optional<Company> company = getOptionalCompany(data);

        Company companyFound = company.get();
        Role roleFound = role.get();
        Employee newEmployee = new Employee(data, roleFound, companyFound);

        employeeRepository.save(newEmployee);

        return new EmployeeResponseDTO(newEmployee);
    }

    private void VerifyEmployeeFindByName(EmployeeRequestDTO data) {
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByName(data.getName()));
        if (employee.isPresent()){
            throw new BadRequestCustomException("Funcionário já cadatrado!");
        }
    }

    @Transactional
    public EmployeeResponseDTO put(EmployeeRequestDTO data){
        Optional<Employee> employee = getOptionalEmployeeFindById(data.getId());
        Optional<Role> role = getOptionalRole(data);
        Optional<Company> company = getOptionalCompany(data);

        Company companyFound = company.get();
        Role roleFound = role.get();
        Employee newEmployee = employee.get();
        SetDadosUpdateEmployee(newEmployee, data, companyFound, roleFound);

        return new EmployeeResponseDTO(newEmployee);
    }

    private Optional<Employee> getOptionalEmployeeFindById(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()){
            throw new BadRequestCustomException("Funcionário não cadatrado!");
        }
        return employee;
    }

    private Optional<Role> getOptionalRole(EmployeeRequestDTO data) {
        Optional<Role> role = roleRepository.findById(data.getRoleId());
        if (!role.isPresent()){
            throw new NotFoundCustomException("Função não cadastrada!");
        }
        return role;
    }

    private Optional<Company> getOptionalCompany(EmployeeRequestDTO data) {
        Optional<Company> company = companyRepository.findById(data.getCompanyId());
        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }
        return company;
    }

    private void SetDadosUpdateEmployee(Employee employee, EmployeeRequestDTO data, Company company, Role role){
        employee.setAdmission(data.getAdmission());
        employee.setName(data.getName());
        employee.setCompany(company);
        employee.setGender(data.getGender());
        employee.setStatus(data.getStatus());
        employee.setRole(role);
    }
}
