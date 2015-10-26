package net.leludo.gtrchamp.dao.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, java.sql.Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate attribute) {
		if (attribute != null) {
			return java.sql.Date.valueOf(attribute);
		}
		return null;
	}

	@Override
	public LocalDate convertToEntityAttribute(Date dbData) {
		if (dbData != null) {
			return dbData.toLocalDate();
		}
		return null;
	}

}
