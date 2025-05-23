package com.controle.ponto.domain.exceptions.rest;

import com.controle.ponto.domain.dto.message.MessageResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        MessageResponseDTO message = new MessageResponseDTO(status.value(), ((HttpStatus) status).getReasonPhrase(), ex.getMessage());

        return ResponseEntity.status(status.value()).body(message);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity userNotFoundHandler(UserNotFoundException ex){
        MessageResponseDTO message = new MessageResponseDTO(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(BadRequestCustomException.class)
    private ResponseEntity badRequestCustomhandler(BadRequestCustomException ex){
        MessageResponseDTO message = new MessageResponseDTO(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(NotFoundCustomException.class)
    private ResponseEntity notFoundCustomHandler(NotFoundCustomException ex){
        MessageResponseDTO message = new MessageResponseDTO(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }
}
