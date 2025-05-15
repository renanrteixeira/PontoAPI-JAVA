package com.controle.ponto.business.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.domain.exceptions.BadRequestCustomException;
import com.controle.ponto.domain.exceptions.NotFoundCustomException;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.persistence.typedate.TypeDateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TypeDateBusiness {

    @Autowired
    private TypeDateRepository typeDateRepository;

    public List<TypeDateResponseDTO> findAll(){
        var typeDates = typeDateRepository.findAll();

        List<TypeDateResponseDTO> typeDateList = new ArrayList<>();

        for (com.controle.ponto.domain.typedate.TypeDate typeDate : typeDates){
            TypeDateResponseDTO typeDateResponseDTO = new TypeDateResponseDTO(typeDate);
            typeDateList.add(typeDateResponseDTO);
        }

        return typeDateList;
    }

    public TypeDateResponseDTO findById(String id){
        Optional<TypeDate> typedate = getTypeDateFindById(id);
        TypeDate typeFound = typedate.get();

        return new TypeDateResponseDTO(typeFound);
    }

    private Optional<TypeDate> getTypeDateFindById(String id) {
        Optional<TypeDate> typedate = typeDateRepository.findById(id);
        if (!typedate.isPresent()){
            throw new NotFoundCustomException("Tipo de data não encontrada!");
        }
        return typedate;
    }

    public TypeDateResponseDTO post(TypeDateRequestDTO data){
        VerifyTypeDateFindByName(data);
        TypeDate newTypeDate = new TypeDate(data);
        typeDateRepository.save(newTypeDate);

        return new TypeDateResponseDTO(newTypeDate);
    }

    private void VerifyTypeDateFindByName(TypeDateRequestDTO data) {
        Optional<TypeDate> typeDate = Optional.ofNullable(typeDateRepository.findByName(data.getName()));
        if (typeDate.isPresent()){
            throw new BadRequestCustomException("Tipo de data já cadastrada!");
        }
    }

    @Transactional
    public TypeDateResponseDTO put(TypeDateRequestDTO data){
        Optional<TypeDate> typeDate = getTypeDateFindById(data.getId());
        TypeDate newTypeDate = typeDate.get();
        newTypeDate.setName(data.getName());
        newTypeDate.setWeekend(data.getWeekend());
        newTypeDate.setTime(data.getTime());

        return new TypeDateResponseDTO(newTypeDate);
    }
}
