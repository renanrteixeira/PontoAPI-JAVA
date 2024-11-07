package com.controle.ponto.domain.enumerator;

import lombok.Getter;

@Getter
public enum TypeHour {
    Plus(0),
    Minus(1);

    private final int type;

    TypeHour(int type) { this.type = type; }
}
