package com.tomislav.kralj.model.service.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tomislav.kralj.model.database.DatabaseObject;
import com.tomislav.kralj.model.service.ServiceObject;

@Service
public interface Converter {
	@Value("${evidencije.date_format}")
	String DATE_FORMAT = "dd.MM.yyyy.";
	DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT); 
	
	
	ServiceObject toDTO(DatabaseObject data);
	
	DatabaseObject toData(ServiceObject dto);

}
