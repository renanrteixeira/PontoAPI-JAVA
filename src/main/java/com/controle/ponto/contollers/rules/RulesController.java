package com.controle.ponto.contollers.rules;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rules")
public class RulesController {

    @GetMapping
    public ResponseEntity getAllRules(){
        return null;
    }
}
