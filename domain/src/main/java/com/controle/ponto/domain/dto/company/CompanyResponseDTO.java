package com.controle.ponto.domain.dto.company;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CompanyResponseDTO {
    @NotNull
    String id;

    @NotNull
    String name;

    @NotNull
    String address;

    @NotNull
    String telephone;

}
