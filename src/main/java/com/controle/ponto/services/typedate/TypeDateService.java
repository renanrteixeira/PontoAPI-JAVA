package com.controle.ponto.services.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.interfaces.services.IService;
import com.controle.ponto.repositories.typedate.TypeDateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeDateService implements IService<TypeDateRequestDTO, TypeDateResponseDTO> {

    @Autowired
    private TypeDateRepository repository;

    public List<TypeDateResponseDTO> findAll(){
        var typeDates = repository.findAll();

        List<TypeDateResponseDTO> typeDateList = new ArrayList<>();

        for (TypeDate typeDate : typeDates){
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
        Optional<TypeDate> typedate = repository.findById(id);
        if (!typedate.isPresent()){
            throw new NotFoundCustomException("Tipo de data não encontrada!");
        }
        return typedate;
    }

    public TypeDateResponseDTO post(TypeDateRequestDTO data){
        VerifyTypeDateFindByName(data);
        TypeDate newTypeDate = new TypeDate(data);
        repository.save(newTypeDate);

        return new TypeDateResponseDTO(newTypeDate);
    }

    private void VerifyTypeDateFindByName(TypeDateRequestDTO data) {
        Optional<TypeDate> typeDate = Optional.ofNullable(repository.findByName(data.getName()));
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
