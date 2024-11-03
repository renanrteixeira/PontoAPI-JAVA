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

        List<EmployeeResponseDTO> listEmployee = new ArrayList<EmployeeResponseDTO>();

        for (Employee employee : employees) {
            EmployeeResponseDTO empl = new EmployeeResponseDTO(employee);
            listEmployee.add(empl);
        }

        return listEmployee;
    }

    public EmployeeResponseDTO findById(String id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent()) {
            throw new NotFoundCustomException("Funcionário não encontrado!");
        }
        Employee foundEmployee = employee.get();

        return  new EmployeeResponseDTO(foundEmployee);

    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data){
        Optional<Employee> employee = Optional.ofNullable(employeeRepository.findByName(data.getName()));
        if (employee.isPresent()){
            throw new BadRequestCustomException("Funcionário já cadatrado!");
        }
        Optional<Role> role = roleRepository.findById(data.getRoleId());
        if (!role.isPresent()){
            throw new NotFoundCustomException("Função não cadastrada!");
        }
        Optional<Company> company = companyRepository.findById(data.getCompanyId());
        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }

        Company companyFound = company.get();
        Role roleFound = role.get();

        Employee newEmployee = new Employee();
        newEmployee.setCompany(companyFound);
        newEmployee.setId(data.getId());
        newEmployee.setName(data.getName());
        newEmployee.setAdmission(data.getAdmission());
        newEmployee.setGender(data.getGender());
        newEmployee.setStatus(data.getStatus());
        newEmployee.setRole(roleFound);

        employeeRepository.save(newEmployee);

        return new EmployeeResponseDTO(newEmployee);
    }

    @Transactional
    public EmployeeResponseDTO put(EmployeeRequestDTO data){
        Optional<Employee> employee = employeeRepository.findById(data.getId());
        if (!employee.isPresent()){
            throw new BadRequestCustomException("Funcionário não cadatrado!");
        }
        Optional<Role> role = roleRepository.findById(data.getRoleId());
        if (!role.isPresent()){
            throw new NotFoundCustomException("Função não cadastrada!");
        }
        Optional<Company> company = companyRepository.findById(data.getCompanyId());
        if (!company.isPresent()){
            throw new NotFoundCustomException("Empresa não cadastrada!");
        }

        Company companyFound = company.get();
        Role roleFound = role.get();

        Employee newEmployee = employee.get();
        newEmployee.setAdmission(data.getAdmission());
        newEmployee.setName(data.getName());
        newEmployee.setCompany(companyFound);
        newEmployee.setGender(data.getGender());
        newEmployee.setStatus(data.getStatus());
        newEmployee.setRole(roleFound);

        return new EmployeeResponseDTO(newEmployee);
    }
}
