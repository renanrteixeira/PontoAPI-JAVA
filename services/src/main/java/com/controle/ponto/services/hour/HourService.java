package com.controle.ponto.services.hour;

import com.controle.ponto.business.hour.HourBusiness;
import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.hour.HourResponseDTO;
import com.controle.ponto.interfaces.ICrudServiceHour;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HourService implements ICrudServiceHour<HourRequestDTO, HourResponseDTO> {

    private static final Logger logger = LoggerFactory.getLogger(HourService.class);

    @Autowired
    private HourBusiness hourBusiness;

    public List<HourResponseDTO> findAll(){
        logger.debug("Delegando busca de todos os registros de horas");
        return hourBusiness.findAll();
    }

    public HourResponseDTO findById(String id){
        logger.debug("Delegando busca de registro de hora por ID: {}", id);
        return hourBusiness.findById(id);
    }

    public List<HourResponseDTO> findByEmployee(String id){
        logger.debug("Delegando busca de registros de horas por funcionário: {}", id);
        return hourBusiness.findByEmployee(id);
    }

    public HourResponseDTO post(HourRequestDTO data){
        logger.debug("Delegando criação de novo registro de hora");
        return hourBusiness.post(data);
    }

    public HourResponseDTO put(HourRequestDTO data){
        logger.debug("Delegando atualização de registro de hora");
        return hourBusiness.put(data);
    }

    public void delete(String id){
        logger.debug("Delegando exclusão de registro de hora por ID: {}", id);
        hourBusiness.delete(id);
    }

}
