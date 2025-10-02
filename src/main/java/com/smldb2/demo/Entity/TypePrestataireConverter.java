package com.smldb2.demo.Entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
@Converter(autoApply = false)
public class TypePrestataireConverter implements AttributeConverter<TypePrestataire, String> {

    @Override
    public String convertToDatabaseColumn(TypePrestataire attribute) {
        return attribute != null ? attribute.getDbValue() : null;
    }

    @Override
    public TypePrestataire convertToEntityAttribute(String dbData) {
        return dbData != null ? TypePrestataire.fromDbValue(dbData) : null;
    }
}

