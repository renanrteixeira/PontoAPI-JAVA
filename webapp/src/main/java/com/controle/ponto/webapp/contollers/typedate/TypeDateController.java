package com.controle.ponto.webapp.contollers.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.typedate.TypeDateService;
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
@RequestMapping("typedate")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Type dates")
@Slf4j
public class TypeDateController implements IContoller<TypeDateRequestDTO> {

    private static final Logger logger = LoggerFactory.getLogger(TypeDateController.class);

    @Autowired
    private TypeDateService service;

    @GetMapping
    public ResponseEntity findAll(){
        logger.info("Buscando todos os tipos de data");
        var typeDate = service.findAll();
        logger.info("Encontrados {} tipos de data", typeDate.size());

        return ResponseEntity.ok(typeDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        logger.info("Buscando tipo de data {}", id);
        var typeDate = service.findById(id);
        logger.info("Tipo de data {} encontrado", id);

        return ResponseEntity.ok(typeDate);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid TypeDateRequestDTO data){
        logger.info("Criando novo tipo de data {}", data.getName());
        var typeDate = service.post(data);
        URI location = URI.create("/typeDate/" + typeDate.getId());
        logger.info("Tipo de data {} criado com sucesso", typeDate.getId());

        return ResponseEntity.created(location).body(typeDate);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid TypeDateRequestDTO data){
        logger.info("Atualizando tipo de data {}", data.getId());
        var typeDate = service.put(data);
        logger.info("Tipo de data {} atualizado com sucesso", typeDate.getId());

        return ResponseEntity.accepted().body(typeDate);
    }
}
