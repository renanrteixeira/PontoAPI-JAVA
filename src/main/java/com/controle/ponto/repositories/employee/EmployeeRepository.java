package com.controle.ponto.repositories.employee;

import com.controle.ponto.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByName(String name);
}
