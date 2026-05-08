package com.controle.ponto.domain.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE('A'),
    INACTIVE('I');

    private final char value;

    UserStatus(char value) {
        this.value = value;
    }

    public static UserStatus fromValue(char value) {
        for (UserStatus status : values()) {
            if (status.value == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid UserStatus value: " + value);
    }

    @JsonValue
    public char getValue() {
        return value;
    }

    @JsonCreator
    public static UserStatus fromValue(String value) {
        if (value == null) return null;

        return switch (value.toUpperCase()) {
            case "A" -> ACTIVE;
            case "I" -> INACTIVE;
            default -> throw new IllegalArgumentException("Valor inválido para UserStatus: " + value);
        };
    }
}
