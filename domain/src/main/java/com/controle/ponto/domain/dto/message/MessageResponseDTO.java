package com.controle.ponto.domain.dto.message;

import jakarta.validation.constraints.NotNull;

public record MessageResponseDTO(
        @NotNull
        int code,
        @NotNull
        String typeError,
        @NotNull
        String message) {
}
