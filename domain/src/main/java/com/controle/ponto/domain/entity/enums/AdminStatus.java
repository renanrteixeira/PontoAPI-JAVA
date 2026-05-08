package com.controle.ponto.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum AdminStatus {
    YES('S'),
    NO('N');

    private final char value;

    AdminStatus(char value) {
        this.value = value;
    }

    public static AdminStatus fromValue(char value) {
        for (AdminStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid AdminStatus value: " + value);
    }

    @JsonValue
    public char getValue() {
        return value;
    }

    @JsonCreator
    public static AdminStatus fromValue(String value) {
        if (value == null) return null;

        return switch (value.toUpperCase()) {
            case "S" -> YES;
            case "N" -> NO;
            default -> throw new IllegalArgumentException("Valor inválido para AdminStatus: " + value);
        };
    }
}
