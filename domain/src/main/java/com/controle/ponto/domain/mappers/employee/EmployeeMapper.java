package com.controle.ponto.domain.mappers.employee;

import com.controle.ponto.domain.dto.employee.EmployeeRequestDTO;
import com.controle.ponto.domain.dto.employee.EmployeeResponseDTO;
import com.controle.ponto.domain.entity.employee.Employee;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    Employee toResquestEntity(EmployeeRequestDTO employeeRequestDTO);

    Employee toResponseEntity(EmployeeResponseDTO employeeResponseDTO);

    @InheritInverseConfiguration
    EmployeeRequestDTO toRequestDTO(Employee employee);

    @InheritInverseConfiguration
    EmployeeResponseDTO toResponseDTO(Employee employee);
}
