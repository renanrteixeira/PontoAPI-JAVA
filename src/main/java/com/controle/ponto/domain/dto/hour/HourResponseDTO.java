package com.controle.ponto.domain.dto.hour;

import com.controle.ponto.domain.enumerator.TypeHour;
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
    String employeeId;

    @NotNull
    Date date;

    @NotNull
    int type;

    @NotNull
    String typeDateId;

    @NotNull
    LocalTime hour1;

    @NotNull
    LocalTime hour2;

    @NotNull
    LocalTime hour3;

    @NotNull
    LocalTime hour4;

    @NotNull
    LocalTime hour5;

    @NotNull
    LocalTime hour6;

    @NotNull
    LocalTime balance;

    public HourResponseDTO(Hour hour){
        this.id = hour.getId();
        this.date = hour.getDate();
        this.employeeId = hour.getEmployee().getId();
        this.typeDateId = hour.getTypeDate().getId();
        this.type = hour.getType();
        this.hour1 = hour.getHour1();
        this.hour2 = hour.getHour2();
        this.hour3 = hour.getHour3();
        this.hour4 = hour.getHour4();
        this.hour5 = hour.getHour5();
        this.hour6 = hour.getHour6();
        this.balance = hour.getBalance();
    }
}
