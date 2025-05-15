package com.controle.ponto.domain.exceptions;

public class BadRequestCustomException extends RuntimeException {

    public BadRequestCustomException(String message) {
        super(message);
    }
}
