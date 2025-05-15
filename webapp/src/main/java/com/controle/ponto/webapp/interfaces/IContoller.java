package com.controle.ponto.webapp.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IContoller<T> {

    ResponseEntity findAll();
    ResponseEntity findById(@PathVariable String id);
    ResponseEntity post(@RequestBody @Valid T data);
    ResponseEntity put(@RequestBody @Valid T data);

}
