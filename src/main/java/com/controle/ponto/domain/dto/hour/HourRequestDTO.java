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
    LocalTime enterMorging;

    @NotNull
    LocalTime exitMorging;

    @NotNull
    LocalTime enterAfternoon;

    @NotNull
    LocalTime exitAfternoon;

    @NotNull
    LocalTime enterOvertime;

    @NotNull
    LocalTime exitOvertime;
}
