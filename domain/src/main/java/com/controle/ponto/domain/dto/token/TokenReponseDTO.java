package com.controle.ponto.domain.dto.token;

import jakarta.validation.constraints.NotNull;

public record TokenReponseDTO(
        @NotNull
        String token
) {
}
