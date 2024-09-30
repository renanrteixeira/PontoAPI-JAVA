package com.controle.ponto.exceptions.rest;

import com.controle.ponto.domain.dto.message.MessageResponseDTO;
import com.controle.ponto.exceptions.user.UserNotFoundException;
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
}
