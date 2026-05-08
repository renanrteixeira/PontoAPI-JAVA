package com.controle.ponto.business.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.mappers.typeDate.TypeDateMapper;
import com.controle.ponto.domain.entity.typedate.TypeDate;
import com.controle.ponto.persistence.typedate.TypeDateRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TypeDateBusiness {

    private static final Logger logger = LoggerFactory.getLogger(TypeDateBusiness.class);

    @Autowired
    private TypeDateRepository typeDateRepository;

    public List<TypeDateResponseDTO> findAll(){
        logger.info("Buscando todos os tipos de data");
        var typeDates = typeDateRepository.findAll();

        List<TypeDateResponseDTO> typeDateList = new ArrayList<>();

        for (TypeDate typeDate : typeDates){
            TypeDateResponseDTO typeDateResponseDTO = TypeDateMapper.INSTANCE.toResponseDTO(typeDate);
            typeDateList.add(typeDateResponseDTO);
        }

        logger.info("Encontrados {} tipos de data", typeDateList.size());
        return typeDateList;
    }

    public TypeDateResponseDTO findById(String id){
        logger.info("Buscando tipo de data por ID: {}", id);
        Optional<TypeDate> typedate = getTypeDateFindById(id);
        TypeDate typeFound = typedate.get();
        logger.info("Tipo de data encontrado: {}", id);

        return TypeDateMapper.INSTANCE.toResponseDTO(typeFound);
    }

    private Optional<TypeDate> getTypeDateFindById(String id) {
        Optional<TypeDate> typedate = typeDateRepository.findById(id);
        if (!typedate.isPresent()){
            logger.warn("Tipo de data não encontrada: {}", id);
            throw new NotFoundCustomException("Tipo de data não encontrada!");
        }
        return typedate;
    }

    public TypeDateResponseDTO post(TypeDateRequestDTO data){
        logger.info("Criando novo tipo de data {}", data.getName());
        VerifyTypeDateFindByName(data);
        TypeDate newTypeDate = new TypeDate(data);
        typeDateRepository.save(newTypeDate);
        logger.info("Tipo de data criado com sucesso");

        return TypeDateMapper.INSTANCE.toResponseDTO(newTypeDate);
    }

    private void VerifyTypeDateFindByName(TypeDateRequestDTO data) {
        Optional<TypeDate> typeDate = Optional.ofNullable(typeDateRepository.findByName(data.getName()));
        if (typeDate.isPresent()){
            logger.warn("Tentativa de criar tipo de data duplicada: {}", data.getName());
            throw new BadRequestCustomException("Tipo de data já cadastrada!");
        }
    }

    @Transactional
    public TypeDateResponseDTO put(TypeDateRequestDTO data){
        logger.info("Atualizando tipo de data {}", data.getId());
        Optional<TypeDate> typeDate = getTypeDateFindById(data.getId());
        TypeDate newTypeDate = typeDate.get();
        newTypeDate.setName(data.getName());
        newTypeDate.setWeekend(data.getWeekend());
        newTypeDate.setTime(data.getTime());
        logger.info("Tipo de data atualizado com sucesso");

        return TypeDateMapper.INSTANCE.toResponseDTO(newTypeDate);
    }
}
