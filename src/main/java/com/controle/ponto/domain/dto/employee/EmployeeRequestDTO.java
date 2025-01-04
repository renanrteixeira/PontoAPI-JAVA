package com.controle.ponto.domain.dto.employee;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeRequestDTO {
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

}
