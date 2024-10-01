package com.controle.ponto.exceptions;

public class BadRequestCustomException extends RuntimeException {

    public BadRequestCustomException(String message) {
        super(message);
    }
}
