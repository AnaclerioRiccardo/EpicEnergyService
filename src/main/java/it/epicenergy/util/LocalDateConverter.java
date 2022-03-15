package it.epicenergy.util;

import java.time.LocalDate;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateConverter implements Converter<String, LocalDate>{

	//Converte una stringa in un LocalDate
	@Override
	public LocalDate convert(String data) {
		return LocalDate.parse(data);
	}


}
