package com.controle.ponto.domain.converter;

import com.controle.ponto.domain.entity.enums.AdminStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.controle.ponto.domain.entity.enums.UserStatus;

@Converter(autoApply = true)
public class UserStatusConverter implements AttributeConverter<UserStatus, Character> {

    @Override
    public Character convertToDatabaseColumn(UserStatus attribute) {
        if (attribute == null) { return null; }
        return attribute == UserStatus.ACTIVE ? 'A' : 'I';
    }

    @Override
    public UserStatus convertToEntityAttribute(Character dbData) {
        if (dbData == null) return null;
        return dbData == 'A' ? UserStatus.ACTIVE : UserStatus.INACTIVE;
    }
}
