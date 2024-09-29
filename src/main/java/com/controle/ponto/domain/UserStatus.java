package com.controle.ponto.domain;

public enum UserStatus {
    Ativo('A'),
    Inativo('I');

    private char status;

    UserStatus(char status){
        this.status = status;
    }

    public char getStatus(){
        return status;
    }
}
