package com.controle.ponto.services.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.interfaces.IService;
import com.controle.ponto.business.typedate.TypeDateBusiness;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeDateService implements IService<TypeDateRequestDTO, TypeDateResponseDTO> {

    @Autowired
    private TypeDateBusiness typeDateBusiness;

    public List<TypeDateResponseDTO> findAll(){
        return typeDateBusiness.findAll();
    }

    public TypeDateResponseDTO findById(String id){
        return typeDateBusiness.findById(id);
    }

    public TypeDateResponseDTO post(TypeDateRequestDTO data){
        return typeDateBusiness.post(data);
    }

    @Transactional
    public TypeDateResponseDTO put(TypeDateRequestDTO data){
        return typeDateBusiness.put(data);
    }

    
}
