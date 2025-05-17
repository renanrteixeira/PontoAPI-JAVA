package com.controle.ponto.domain.mappers.role;

import com.controle.ponto.domain.dto.role.RoleRequestDTO;
import com.controle.ponto.domain.dto.role.RoleResponseDTO;
import com.controle.ponto.domain.entity.role.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role toResquestEntity(RoleRequestDTO roleRequestDTO);

    Role toResponseEntity(Role role);

    @InheritInverseConfiguration
    RoleRequestDTO toRequestDTO(Role role);

    @InheritInverseConfiguration
    RoleResponseDTO toResponseDTO(Role role);
}
