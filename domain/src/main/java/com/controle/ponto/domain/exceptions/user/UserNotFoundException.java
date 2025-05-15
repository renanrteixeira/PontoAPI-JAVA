package com.controle.ponto.domain.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){ super("Usuário ou senha inválidos!"); }

    public UserNotFoundException(String message) {
        super(message);
    }
}
