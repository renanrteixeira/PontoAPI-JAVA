package com.controle.ponto.webapp.contollers.company;

import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.company.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/company")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Companies")
public class CompanyController implements IContoller<CompanyRequestDTO> {

    @Autowired
    private CompanyService service;

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
        var company = service.post(data);

        URI location = URI.create("/com/controle/ponto/persistence/role/" + company.getId());

        return ResponseEntity.created(location).body(company);
    }

    public ResponseEntity put(@RequestBody @Valid CompanyRequestDTO data){
        var company = service.put(data);

        return ResponseEntity.accepted().body(company);
    }

}
