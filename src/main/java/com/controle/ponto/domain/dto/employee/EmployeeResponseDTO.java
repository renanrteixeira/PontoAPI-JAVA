package com.controle.ponto.domain.dto.employee;

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
    String roleId;

    @NotNull
    String companyId;

    public EmployeeResponseDTO(Employee employee){
        this.id = employee.getId();
        this.name = employee.getName();
        this.admission = employee.getAdmission();
        this.gender = employee.getGender();
        this.status = employee.getStatus();
        this.roleId = employee.getRole().getId();
        this.companyId = employee.getCompany().getId();
    }
}
