package com.controle.ponto.services.employee;

import com.controle.ponto.business.employee.EmployeeBusiness;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeBusiness employeeBusiness;

    public List<EmployeeResponseDTO> findAll(String companyId){
        logger.debug("Delegando busca de todos os funcionários para empresa {}", companyId);
        return employeeBusiness.findAll(companyId);
    }

    public Page<EmployeeResponseDTO> findAllPaginated(String companyId, Pageable pageable){
        logger.debug("Delegando busca paginada de funcionários para empresa {} com paginação {}", companyId, pageable);
        return employeeBusiness.findAllPaginated(companyId, pageable);
    }

    public EmployeeResponseDTO findById(String id, String companyId){
        logger.debug("Delegando busca de funcionário {} para empresa {}", id, companyId);
        return employeeBusiness.findById(id, companyId);
    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data, String companyId){
        logger.debug("Delegando criação de funcionário {} para empresa {}", data.getName(), companyId);
        return employeeBusiness.post(data, companyId);
    }

    public EmployeeResponseDTO put(EmployeeRequestDTO data, String companyId){
        logger.debug("Delegando atualização de funcionário {} para empresa {}", data.getId(), companyId);
        return employeeBusiness.put(data, companyId);
    }

}
