package com.controle.ponto.domain.dto.hour;

import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.domain.hour.Hour;
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

    public HourResponseDTO(Hour hour){
        this.id = hour.getId();
        this.date = hour.getDate();
        EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO(hour.getEmployee());
        this.setEmployee(employeeResponseDTO);
        TypeDateResponseDTO typeDateResponseDTO = new TypeDateResponseDTO(hour.getTypeDate());
        this.setTypeDate(typeDateResponseDTO);
        this.isNegative = hour.getIsNegative();
        this.enterMorning = hour.getEnterMorning();
        this.exitMorning = hour.getExitMorning();
        this.enterAfternoon = hour.getEnterAfternoon();
        this.exitAfternoon = hour.getExitAfternoon();
        this.enterOvertime = hour.getEnterOvertime();
        this.exitOvertime = hour.getExitOvertime();
        this.balance = hour.getBalance();
    }
}
