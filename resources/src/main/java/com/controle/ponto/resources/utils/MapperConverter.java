package com.controle.ponto.resources.utils;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MapperConverter<T> {

    @Autowired
    private ObjectMapper objectMapper;

    public Map<T, Object> objectMap(Object data){
        return objectMapper.convertValue(data, new TypeReference<Map<T, Object>>() {});
    }
}
