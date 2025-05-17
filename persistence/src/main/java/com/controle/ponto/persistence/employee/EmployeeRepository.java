package com.controle.ponto.persistence.employee;

import com.controle.ponto.domain.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByName(String name);
}
