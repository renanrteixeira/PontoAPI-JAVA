package com.controle.ponto.services.hour;

import com.controle.ponto.business.hour.HourBusiness;
import com.controle.ponto.domain.dto.hour.HourRequestDTO;
import com.controle.ponto.domain.dto.hour.HourResponseDTO;
import com.controle.ponto.interfaces.IServiceHour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HourService implements IServiceHour<HourRequestDTO, HourResponseDTO> {

    @Autowired
    private HourBusiness hourBusiness;

    public List<HourResponseDTO> findAll(){
        return hourBusiness.findAll();
    }

    public HourResponseDTO findById(String id){
        return hourBusiness.findById(id);
    }

    public List<HourResponseDTO> findByEmployee(String id){
        return hourBusiness.findByEmployee(id);
    }

    public HourResponseDTO post(HourRequestDTO data){
        return hourBusiness.post(data);
    }

    public HourResponseDTO put(HourRequestDTO data){
        return hourBusiness.put(data);
    }

    public void delete(String id){
        hourBusiness.delete(id);
    }

}
