package com.controle.ponto.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.controle.ponto.domain.entity.enums.AdminStatus;

@Converter(autoApply = true)
public class AdminStatusConverter implements AttributeConverter<AdminStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(AdminStatus attribute) {
        if (attribute == null) { return null; }
        return attribute == AdminStatus.YES ? 'S' : 'N';
    }

    @Override
    public AdminStatus convertToEntityAttribute(Character dbData) {
        if (dbData == null) return null;
        return dbData == 'S' ? AdminStatus.YES : AdminStatus.NO;
    }
}
