package com.controle.ponto.domain.mappers.hour;

import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.hour.HourResponseDTO;
import com.controle.ponto.domain.entity.hour.Hour;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HourMapper {

    HourMapper INSTANCE = Mappers.getMapper(HourMapper.class);

    Hour toResquestEntity(HourRequestDTO hourRequestDTO);

    Hour toResponseEntity(HourResponseDTO hourResponseDTO);

    @InheritInverseConfiguration
    HourRequestDTO toRequestDTO(Hour hour);

    @InheritInverseConfiguration
    HourResponseDTO toResponseDTO(Hour hour);

}
