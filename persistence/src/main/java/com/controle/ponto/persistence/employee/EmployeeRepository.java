package com.controle.ponto.persistence.employee;

import com.controle.ponto.domain.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Employee findByName(String name);

    @Query("SELECT e FROM employees e LEFT JOIN FETCH e.role LEFT JOIN FETCH e.company")
    List<Employee> findAllWithRoleAndCompany();

    @Query("SELECT e FROM employees e LEFT JOIN FETCH e.role LEFT JOIN FETCH e.company WHERE e.id = :id")
    Optional<Employee> findByIdWithRoleAndCompany(String id);
}
