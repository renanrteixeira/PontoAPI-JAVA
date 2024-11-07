package com.controle.ponto.domain.dto.hour;
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
public class HourRequestDTO {
    String id;

    @NotNull
    String employeeId;

    @NotNull
    Date date;

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
}
