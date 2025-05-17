package com.controle.ponto.domain.mappers.user;

import com.controle.ponto.domain.dto.user.UserRequestDTO;
import com.controle.ponto.domain.dto.user.UserResponseDTO;
import com.controle.ponto.domain.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANTE = Mappers.getMapper(UserMapper.class);

    User toResquestEntity(UserRequestDTO userRequestDTO);


    User toResponseEntity(UserResponseDTO userResponseDTO);

    @InheritInverseConfiguration
    UserRequestDTO toRequestDTO(User user);

    @InheritInverseConfiguration
    UserResponseDTO toResponseDTO(User user);
}
