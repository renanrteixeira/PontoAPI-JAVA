package com.controle.ponto.webapp.contollers.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.role.RoleService;
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
@RequestMapping("/role")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Roles")
@Slf4j
public class RoleController implements IContoller<RoleRequestDTO> {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService service;

    @GetMapping
    public ResponseEntity findAll(){
        logger.info("Buscando todas as funções");
        var roles = service.findAll();
        logger.info("Encontradas {} funções", roles.size());

        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        logger.info("Buscando função {}", id);
        var role = service.findById(id);
        logger.info("Função {} encontrada", id);

        return ResponseEntity.ok(role);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid RoleRequestDTO data){
        logger.info("Criando nova função {}", data.getName());
        var role = service.post(data);
        URI location = URI.create("/role/" + role.getId());
        logger.info("Função {} criada com sucesso", role.getId());

        return ResponseEntity.created(location).body(role);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid RoleRequestDTO data){
        logger.info("Atualizando função {}", data.getId());
        var role = service.put(data);
        logger.info("Função {} atualizada com sucesso", role.getId());

        return ResponseEntity.accepted().body(role);
    }
}
