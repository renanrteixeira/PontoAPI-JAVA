package com.controle.ponto.webapp.contollers.employee;

import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.dto.common.PaginationResponse;
import com.controle.ponto.services.employee.EmployeeService;
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

@RestController
@RequestMapping("/employee")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "employees")
@Slf4j
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity findAll(@RequestHeader("X-Company-Id") String companyId){
        logger.info("Buscando todos os funcionários para a empresa: {}", companyId);
        var employees = employeeService.findAll(companyId);
        logger.info("Encontrados {} funcionários para a empresa {}", employees.size(), companyId);

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/paginated")
    public ResponseEntity<PaginationResponse<EmployeeResponseDTO>> findAllPaginated(
            @RequestHeader("X-Company-Id") String companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        logger.info("Buscando funcionários paginados para empresa {}: página={}, tamanho={}, ordenação={}, direção={}",
                companyId, page, size, sort, direction);
        Pageable pageable = PageRequest.of(page, size, direction, sort);
        Page<EmployeeResponseDTO> employees = employeeService.findAllPaginated(companyId, pageable);
        PaginationResponse<EmployeeResponseDTO> response = new PaginationResponse<>(employees);
        logger.info("Retornando {} funcionários da página {} de {} para empresa {}",
                response.getContent().size(), response.getCurrentPage(), response.getTotalPages(), companyId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@RequestHeader("X-Company-Id") String companyId, @PathVariable String id){
        logger.info("Buscando funcionário {} para empresa {}", id, companyId);
        var employee = employeeService.findById(id, companyId);
        logger.info("Funcionário {} encontrado para empresa {}", id, companyId);

        return ResponseEntity.ok(employee);
    }

    @PostMapping
    public ResponseEntity post(@RequestHeader("X-Company-Id") String companyId, @RequestBody @Valid EmployeeRequestDTO data){
        logger.info("Criando novo funcionário {} para empresa {}", data.getName(), companyId);
        var employee = employeeService.post(data, companyId);
        URI location = URI.create("/com/controle/ponto/persistence/employee/" + employee.getId());
        logger.info("Funcionário {} criado com sucesso para empresa {}", employee.getId(), companyId);

        return ResponseEntity.created(location).body(employee);
    }

    @PutMapping
    public ResponseEntity put(@RequestHeader("X-Company-Id") String companyId, @RequestBody @Valid EmployeeRequestDTO data){
        logger.info("Atualizando funcionário {} para empresa {}", data.getId(), companyId);
        var employee = employeeService.put(data, companyId);
        logger.info("Funcionário {} atualizado com sucesso para empresa {}", employee.getId(), companyId);

        return ResponseEntity.accepted().body(employee);
    }

}
