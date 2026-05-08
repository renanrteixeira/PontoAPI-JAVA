package com.controle.ponto.webapp.contollers.company;

import com.controle.ponto.domain.dto.company.CompanyRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.company.CompanyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/company")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Companies")
@Slf4j
public class CompanyController implements IContoller<CompanyRequestDTO> {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService service;

    @GetMapping
    public ResponseEntity findAll(){
        logger.info("Buscando todas as empresas");
        var companies = service.findAll();
        logger.info("Encontradas {} empresas", companies.size());

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        logger.info("Buscando empresa {}", id);
        var company = service.findById(id);
        logger.info("Empresa {} encontrada", id);

        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid CompanyRequestDTO data){
        logger.info("Criando nova empresa {}", data.getName());
        var company = service.post(data);

        URI location = URI.create("/com/controle/ponto/persistence/role/" + company.getId());
        logger.info("Empresa {} criada com sucesso", company.getId());

        return ResponseEntity.created(location).body(company);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid CompanyRequestDTO data){
        logger.info("Atualizando empresa {}", data.getId());
        var company = service.put(data);
        logger.info("Empresa {} atualizada com sucesso", company.getId());

        return ResponseEntity.accepted().body(company);
    }
}
