package com.controle.ponto.webapp.contollers.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.typedate.TypeDateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("typedate")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Type dates")
public class TypeDateController implements IContoller<TypeDateRequestDTO> {

    @Autowired
    private TypeDateService service;

    @GetMapping
    public ResponseEntity findAll(){
        var typeDate = service.findAll();

        return ResponseEntity.ok(typeDate);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var typeDate = service.findById(id);

        return ResponseEntity.ok(typeDate);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid TypeDateRequestDTO data){
        var typeDate = service.post(data);
        URI location = URI.create("/typeDate/" + typeDate.getId());

        return ResponseEntity.created(location).body(typeDate);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid TypeDateRequestDTO data){
        var typeDate = service.put(data);

        return ResponseEntity.accepted().body(typeDate);
    }
}
