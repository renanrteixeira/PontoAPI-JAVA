package com.controle.ponto.webapp.contollers.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.dto.common.PaginationResponse;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Users")
@Slf4j
public class UserController implements IContoller<UserRequestDTO> {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity findAll(){
        logger.info("Buscando todos os usuários");
        var users = userService.findAll();
        logger.info("Encontrados {} usuários", users.size());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginationResponse<UserResponseDTO>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        logger.info("Buscando usuários paginados: página={}, tamanho={}, ordenação={}, direção={}",
                page, size, sort, direction);
        Pageable pageable = PageRequest.of(page, size, direction, sort);
        Page<UserResponseDTO> users = userService.findAllPaginated(pageable);
        PaginationResponse<UserResponseDTO> response = new PaginationResponse<>(users);
        logger.info("Retornando {} usuários da página {} de {} total",
                response.getContent().size(), response.getCurrentPage(), response.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        logger.info("Buscando usuário {}", id);
        var user = userService.findById(id);
        logger.info("Usuário {} encontrado", id);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid UserRequestDTO data){
        logger.info("Criando novo usuário {}", data.getUsername());
        UserResponseDTO user = userService.post(data);
        URI location = URI.create("/user/" + user.getId());
        logger.info("Usuário {} criado com sucesso", user.getId());

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid UserRequestDTO data){
        logger.info("Atualizando usuário {}", data.getId());
        UserResponseDTO user = userService.put(data);
        logger.info("Usuário {} atualizado com sucesso", user.getId());

        return ResponseEntity.accepted().body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patch(@RequestBody UserRequestDTO data, @PathVariable String id){
        logger.info("Aplicando patch no usuário {}", id);
        UserResponseDTO user = userService.patch(data, id);
        logger.info("Patch aplicado com sucesso no usuário {}", id);

        return ResponseEntity.accepted().body(null);
    }
}
