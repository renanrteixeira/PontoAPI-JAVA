package com.controle.ponto.webapp.contollers.employee;

import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.dto.common.PaginationResponse;
import com.controle.ponto.webapp.interfaces.IContoller;
import com.controle.ponto.services.employee.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "employees")
public class EmployeeController implements IContoller<EmployeeRequestDTO> {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity findAll(){
        var employees = employeeService.findAll();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginationResponse<EmployeeResponseDTO>> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, direction, sort);
        Page<EmployeeResponseDTO> employees = employeeService.findAllPaginated(pageable);
        PaginationResponse<EmployeeResponseDTO> response = new PaginationResponse<>(employees);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id){
        var employee = employeeService.findById(id);

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody @Valid EmployeeRequestDTO data){
        var employee = employeeService.post(data);
        URI location = URI.create("/com/controle/ponto/persistence/employee/" + employee.getId());

        return ResponseEntity.created(location).body(employee);
    }

    @PutMapping
    public ResponseEntity put(@RequestBody @Valid EmployeeRequestDTO data){
        var employee = employeeService.put(data);

        return ResponseEntity.accepted().body(employee);
    }

}
