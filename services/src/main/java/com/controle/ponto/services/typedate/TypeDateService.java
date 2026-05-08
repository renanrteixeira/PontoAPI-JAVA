package com.controle.ponto.services.typedate;

import com.controle.ponto.domain.dto.typedate.TypeDateRequestDTO;
import com.controle.ponto.domain.dto.typedate.TypeDateResponseDTO;
import com.controle.ponto.interfaces.ICrudService;
import com.controle.ponto.business.typedate.TypeDateBusiness;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TypeDateService implements ICrudService<TypeDateRequestDTO, TypeDateResponseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(TypeDateService.class);

    @Autowired
    private TypeDateBusiness typeDateBusiness;

    public List<TypeDateResponseDTO> findAll(){
        logger.debug("Delegando busca de todos os tipos de data");
        return typeDateBusiness.findAll();
    }

    public TypeDateResponseDTO findById(String id){
        logger.debug("Delegando busca de tipo de data por ID: {}", id);
        return typeDateBusiness.findById(id);
    }

    public TypeDateResponseDTO post(TypeDateRequestDTO data){
        logger.debug("Delegando criação de novo tipo de data");
        return typeDateBusiness.post(data);
    }

    @Transactional
    public TypeDateResponseDTO put(TypeDateRequestDTO data){
        logger.debug("Delegando atualização de tipo de data");
        return typeDateBusiness.put(data);
    }

    
}
