package com.controle.ponto.business.employee;

import com.controle.ponto.domain.entity.company.Company;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.entity.employee.Employee;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.employee.EmployeeMapper;
import com.controle.ponto.domain.entity.role.Role;
import com.controle.ponto.persistence.company.CompanyRepository;
import com.controle.ponto.persistence.employee.EmployeeRepository;
import com.controle.ponto.persistence.role.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeBusiness {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public List<EmployeeResponseDTO> findAll(){
        var employees = employeeRepository.findAll();

        List<EmployeeResponseDTO> listEmployee = new ArrayList<>();

        for (Employee employee : employees) {
            Optional<Role> role = roleRepository.findById(employee.getRole().getId());
            Optional<Company> company = companyRepository.findById(employee.getCompany().getId());
            employee.setRole(role.get());
            employee.setCompany(company.get());
            EmployeeResponseDTO empl = EmployeeMapper.INSTANCE.toResponseDTO(employee);
            listEmployee.add(empl);
        }

        return listEmployee;
    }

    public EmployeeResponseDTO findById(String id){
        Optional<Employee> employee = getOptionalEmployeeFindById(id);
        Employee foundEmployee = employee.get();
        Optional<Role> role = roleRepository.findById(foundEmployee.getRole().getId());
        Optional<Company> company = companyRepository.findById(foundEmployee.getCompany().getId());
        foundEmployee.setRole(role.get());
        foundEmployee.setCompany(company.get());

        return EmployeeMapper.INSTANCE.toResponseDTO(foundEmployee);

    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data){
        VerifyEmployeeFindByName(data);
        Optional<Role> role = getOptionalRole(data);
        Optional<Company> company = getOptionalCompany(data);

        Company companyFound = company.get();
        Role roleFound = role.get();
        Employee newEmployee = new Employee(data, roleFound, companyFound);

        employeeRepository.save(newEmployee);

        return EmployeeMapper.INSTANCE.toResponseDTO(newEmployee);
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

        return EmployeeMapper.INSTANCE.toResponseDTO(newEmployee);
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

    private void SetDadosUpdateEmployee(Employee target, EmployeeRequestDTO source, Company company, Role role){
        target.setAdmission(source.getAdmission());
        target.setName(source.getName());
        target.setCompany(company);
        target.setGender(source.getGender());
        target.setStatus(source.getStatus());
        target.setRole(role);
    }

}
