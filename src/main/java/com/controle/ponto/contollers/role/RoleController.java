package com.controle.ponto.contollers.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.services.role.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService service;

    @GetMapping
    public ResponseEntity getAllRules(){
        var roles = service.getAllRoles();

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        var role = service.getById(id);

        if (role == null){
            throw new NotFoundCustomException("Função não encontrada!");
        }

        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity postRole(@RequestBody @Valid RoleRequestDTO data){
        var role = service.postRole(data);
        if (role.getId() == null){
            throw new BadRequestCustomException("Função já cadastrada.");
        }
        URI location = URI.create("/role/" + role.getId());

        return ResponseEntity.created(location).body(role);
    }

    @PutMapping
    public ResponseEntity putRole(@RequestBody @Valid RoleRequestDTO data){
        var role = service.putRole(data);
        if (role == null){
            throw new NotFoundCustomException("Função não encontrada!");
        }

        return ResponseEntity.accepted().body(role);
    }
}