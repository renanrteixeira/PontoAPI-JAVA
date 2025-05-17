package com.controle.ponto.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserResponseDTO {

    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private char admin;
    @NotNull
    private char status;

}
