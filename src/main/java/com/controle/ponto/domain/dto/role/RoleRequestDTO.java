package com.controle.ponto.domain.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RoleRequestDTO {

    private String id;
    @NotNull
    private String name;

}