package com.controle.ponto.domain.mappers.typeDate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.domain.entity.typedate.TypeDate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TypeDateMapper {

    TypeDateMapper INSTANCE = Mappers.getMapper(TypeDateMapper.class);

    @Mapping(target = "hour", ignore = true)
    TypeDate toResquestEntity(TypeDateRequestDTO typeDateRequestDTO);

    TypeDate toResponseEntity(TypeDateResponseDTO typeDateResponseDTO);

    @InheritInverseConfiguration
    TypeDateRequestDTO toRequestDTO(TypeDate typeDate);

    @InheritInverseConfiguration
    TypeDateResponseDTO toResponseDTO(TypeDate typeDate);
}
