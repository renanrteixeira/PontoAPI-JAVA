package com.controle.ponto.exceptions.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){ super("Usuário ou senha inválidos!"); }

    public UserNotFoundException(String message) {
        super(message);
    }
}
