package com.controle.ponto.webapp.contollers.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.webapp.interfaces.IControllerHour;
import com.controle.ponto.services.hour.HourService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/hour")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Hours")
public class HourController implements IControllerHour<HourRequestDTO> {

    @Autowired
    private HourService service;

    @GetMapping
    public ResponseEntity findAll(){
        var hours = service.findAll();

        return ResponseEntity.ok(hours);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var hour = service.findById(id);

        return ResponseEntity.ok(hour);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity findByEmployee(@PathVariable String id){
    var hours = service.findByEmployee(id);

    return ResponseEntity.ok(hours);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid HourRequestDTO data){
        var hour = service.post(data);
        URI location = URI.create("/hour/" + hour.getId());

        return ResponseEntity.created(location).body(hour);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid HourRequestDTO data){
        var hour = service.put(data);

        return ResponseEntity.accepted().body(hour);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable @Valid String id){
        service.delete(id);

        return ResponseEntity.ok().build();
    }
}
