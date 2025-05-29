package com.controle.ponto.services.employee;

import com.controle.ponto.business.employee.EmployeeBusiness;
import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.interfaces.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements IService<EmployeeRequestDTO, EmployeeResponseDTO> {

    @Autowired
    private EmployeeBusiness employeeBusiness;

    public List<EmployeeResponseDTO> findAll(){
        return employeeBusiness.findAll();
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
