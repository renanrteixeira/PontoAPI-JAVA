package com.controle.ponto.contollers.employee;

import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.interfaces.controllers.IContoller;
import com.controle.ponto.services.employee.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/employee")
public class EmployeeController implements IContoller<EmployeeRequestDTO> {

    @Autowired
    private EmployeeService service;

    @GetMapping
    public ResponseEntity findAll(){
        var employees = service.findAll();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var employee = service.findById(id);

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid EmployeeRequestDTO data){
        var employee = service.post(data);
        URI location = URI.create("/employee/" + employee.getId());

        return ResponseEntity.created(location).body(employee);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid EmployeeRequestDTO data){
        var employee = service.put(data);

        return ResponseEntity.accepted().body(employee);
    }
}
