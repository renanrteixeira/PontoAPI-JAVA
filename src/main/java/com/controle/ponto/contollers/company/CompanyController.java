package com.controle.ponto.contollers.company;

import com.controle.ponto.domain.company.Company;
import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.services.company.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService service;

    @GetMapping
    public ResponseEntity findAll(){
        var companies = service.findAll();

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var company = service.findById(id);

        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid CompanyRequestDTO data){
        Company company = service.post(data);

        URI location = URI.create("/role/" + company.getId());

        return ResponseEntity.created(location).body(company);
    }

    public ResponseEntity put(@RequestBody @Valid CompanyRequestDTO data){
        Company company = service.put(data);

        return ResponseEntity.accepted().body(company);
    }

}
