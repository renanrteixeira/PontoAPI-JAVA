package com.controle.ponto.webapp.contollers.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.webapp.interfaces.IControllerHour;
import com.controle.ponto.services.hour.HourService;
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
@RequestMapping("/hour")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Hours")
@Slf4j
public class HourController implements IControllerHour<HourRequestDTO> {

    private static final Logger logger = LoggerFactory.getLogger(HourController.class);

    @Autowired
    private HourService service;

    @GetMapping
    public ResponseEntity findAll(){
        logger.info("Buscando todos os registros de horas");
        var hours = service.findAll();
        logger.info("Encontrados {} registros de horas", hours.size());

        return ResponseEntity.ok(hours);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        logger.info("Buscando registro de horas {}", id);
        var hour = service.findById(id);
        logger.info("Registro de horas {} encontrado", id);

        return ResponseEntity.ok(hour);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity findByEmployee(@PathVariable String id){
        logger.info("Buscando registros de horas para funcionário {}", id);
        var hours = service.findByEmployee(id);
        logger.info("Encontrados {} registros de horas para funcionário {}", hours.size(), id);

        return ResponseEntity.ok(hours);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid HourRequestDTO data){
        logger.info("Criando novo registro de horas para funcionário {}", data.getEmployeeId());
        var hour = service.post(data);
        URI location = URI.create("/hour/" + hour.getId());
        logger.info("Registro de horas {} criado com sucesso", hour.getId());

        return ResponseEntity.created(location).body(hour);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid HourRequestDTO data){
        logger.info("Atualizando registro de horas {}", data.getId());
        var hour = service.put(data);
        logger.info("Registro de horas {} atualizado com sucesso", hour.getId());

        return ResponseEntity.accepted().body(hour);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable @Valid String id){
        logger.info("Removendo registro de horas {}", id);
        service.delete(id);
        logger.info("Registro de horas {} removido com sucesso", id);

        return ResponseEntity.ok().build();
    }
}
