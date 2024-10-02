package com.controle.ponto.services.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.typedate.TypeDate;
import com.controle.ponto.exceptions.BadRequestCustomException;
import com.controle.ponto.exceptions.NotFoundCustomException;
import com.controle.ponto.repositories.TypeDateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeDateService {

    @Autowired
    TypeDateRepository repository;

    public List<TypeDate> findAll(){
        var typeDates = repository.findAll();

        return typeDates;
    }

    public TypeDate findById(String id){
        Optional<TypeDate> typedate = repository.findById(id);
        if (!typedate.isPresent()){
            throw new NotFoundCustomException("Tipo de data não encotrada!");
        }
        TypeDate typeFound = typedate.get();

        return typeFound;
    }

    public TypeDate post(TypeDateRequestDTO data){
        Optional<TypeDate> typeDate = Optional.ofNullable(repository.findByName(data.getName()));
        if (typeDate.isPresent()){
            throw new BadRequestCustomException("Tipo de data já cadastrada!");
        }
        TypeDate newTypeDate = new TypeDate(data);
        repository.save(newTypeDate);

        return newTypeDate;
    }

    @Transactional
    public TypeDate put(TypeDateRequestDTO data){
        Optional<TypeDate> typeDate = repository.findById(data.getId());
        if (typeDate.isPresent()){
            throw new NotFoundCustomException("Tipo de data não encontrada!");
        }
        TypeDate newTypeDate = typeDate.get();
        newTypeDate.setName(data.getName());
        newTypeDate.setWeekend(data.getWeekend());
        newTypeDate.setTime(data.getTime());

        return newTypeDate;
    }

    
}
