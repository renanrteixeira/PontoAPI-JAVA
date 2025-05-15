package com.controle.ponto.webapp.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IControllerHour<T> extends IContoller<T> {
    ResponseEntity findByEmployee(@PathVariable String id);
    ResponseEntity delete(@PathVariable @Valid String id);
}
