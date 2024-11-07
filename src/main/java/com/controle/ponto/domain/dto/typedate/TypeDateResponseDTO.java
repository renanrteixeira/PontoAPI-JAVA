package com.controle.ponto.domain.dto.typedate;

import com.controle.ponto.domain.typedate.TypeDate;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TypeDateResponseDTO {
    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    LocalTime time;

    @NotNull
    char weekend;

    public TypeDateResponseDTO(TypeDate typeDate){
        this.id = typeDate.getId();
        this.name = typeDate.getName();
        this.time = typeDate.getTime();
        this.weekend = typeDate.getWeekend();
    }
}
