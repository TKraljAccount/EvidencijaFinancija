package com.tomislav.kralj.model.service.converter;

import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.model.service.PersonDTO;
import com.tomislav.kralj.repository.PersonRepository;

public abstract class PersonConverter implements Converter {
	
	public static PersonDTO toDTO(Person data) {
		PersonDTO dto = new PersonDTO();
		
		dto.setPersonId(data.getPersonId());
		dto.setUsername(data.getUsername());
		dto.setPassword(data.getPassword());
		dto.setFirstName(data.getFirstName());
		dto.setLastName(data.getLastName());
		dto.setEmail(data.getEmail());
		
		return dto;
	}
	
	public static Person toData(PersonDTO dto, PersonRepository personRepository) {
		Person data = personRepository.findById(dto.getPersonId()).orElse(new Person());
		
		data.setPersonId(dto.getPersonId());
		data.setUsername(dto.getUsername());
		data.setPassword(dto.getPassword());
		data.setFirstName(dto.getFirstName());
		data.setLastName(dto.getLastName());
		data.setEmail(dto.getEmail());
		
		return data;
	}

}
