package com.controle.ponto.domain.dto.token;

import jakarta.validation.constraints.NotNull;

public record TokenRequestDTO(
        @NotNull
        String username,
        @NotNull
        String password
) {
}
