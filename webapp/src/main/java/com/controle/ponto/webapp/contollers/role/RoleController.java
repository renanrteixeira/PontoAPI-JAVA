package com.controle.ponto.webapp.contollers.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.role.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/role")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Roles")
public class RoleController implements IContoller<RoleRequestDTO> {

    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity findAll(){
        var roles = service.findAll();

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var role = service.findById(id);

        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid RoleRequestDTO data){
        var role = service.post(data);
        URI location = URI.create("/role/" + role.getId());

        return ResponseEntity.created(location).body(role);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid RoleRequestDTO data){
        var role = service.put(data);

        return ResponseEntity.accepted().body(role);
    }
}
