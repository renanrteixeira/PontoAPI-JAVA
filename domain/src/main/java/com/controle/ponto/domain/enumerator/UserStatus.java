package com.controle.ponto.domain.enumerator;

import lombok.Getter;

@Getter
public enum UserStatus {
    Ativo('A'),
    Inativo('I');

    private final char status;

    UserStatus(char status){
        this.status = status;
    }

}
