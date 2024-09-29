package com.controle.ponto.domain.dto.user;

import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
        String id,
        @NotNull
        String name,
        @NotNull
        String email,
        @NotNull
        String username,
        @NotNull
        String password,
        @NotNull
        char admin,
        @NotNull
        char status) {
}
