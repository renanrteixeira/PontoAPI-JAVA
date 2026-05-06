package com.controle.ponto.services.employee;

import com.controle.ponto.business.employee.EmployeeBusiness;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.interfaces.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements ICrudService<EmployeeRequestDTO, EmployeeResponseDTO> {

    @Autowired
    private EmployeeBusiness employeeBusiness;

    public List<EmployeeResponseDTO> findAll(){
        return employeeBusiness.findAll();
    }

    public Page<EmployeeResponseDTO> findAllPaginated(Pageable pageable){
        return employeeBusiness.findAllPaginated(pageable);
    }

    public EmployeeResponseDTO findById(String id){
        return employeeBusiness.findById(id);
    }

    public EmployeeResponseDTO post(EmployeeRequestDTO data){
        return employeeBusiness.post(data);
    }

    public EmployeeResponseDTO put(EmployeeRequestDTO data){
        return employeeBusiness.put(data);
    }

}
