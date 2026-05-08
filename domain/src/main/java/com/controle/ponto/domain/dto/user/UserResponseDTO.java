package com.controle.ponto.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import com.controle.ponto.domain.entity.enums.AdminStatus;
import com.controle.ponto.domain.entity.enums.UserStatus;

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
    private AdminStatus admin;
    @NotNull
    private UserStatus status;

}
