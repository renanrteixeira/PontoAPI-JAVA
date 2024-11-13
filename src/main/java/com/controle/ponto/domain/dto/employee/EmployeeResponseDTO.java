package com.controle.ponto.domain.dto.employee;

import com.controle.ponto.domain.dto.company.CompanyResponseDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.domain.employee.Employee;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeResponseDTO {
    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    Date admission;

    @NotNull
    char gender;

    @NotNull
    char status;

    @NotNull
    RoleResponseDTO role;

    @NotNull
    CompanyResponseDTO company;

    public EmployeeResponseDTO(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.admission = employee.getAdmission();
        this.gender = employee.getGender();
        this.status = employee.getStatus();
        RoleResponseDTO role = new RoleResponseDTO(employee.getRole());
        this.setRole(role);
        CompanyResponseDTO company = new CompanyResponseDTO(employee.getCompany());
        this.setCompany(company);
    }
}
