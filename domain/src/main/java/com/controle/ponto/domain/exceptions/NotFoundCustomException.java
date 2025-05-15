package com.controle.ponto.domain.exceptions;

public class NotFoundCustomException extends RuntimeException {

    public NotFoundCustomException(String message) {
        super(message);
    }
}
