package com.controle.ponto.domain.dto.hour;

import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HourResponseDTO {
    @NotNull
    String id;

    @NotNull
    EmployeeResponseDTO employee;

    @NotNull
    Date date;

    @NotNull
    int isNegative;

    @NotNull
    TypeDateResponseDTO typeDate;

    @NotNull
    LocalTime enterMorning;

    @NotNull
    LocalTime exitMorning;

    @NotNull
    LocalTime enterAfternoon;

    @NotNull
    LocalTime exitAfternoon;

    @NotNull
    LocalTime enterOvertime;

    @NotNull
    LocalTime exitOvertime;

    @NotNull
    LocalTime balance;

}
