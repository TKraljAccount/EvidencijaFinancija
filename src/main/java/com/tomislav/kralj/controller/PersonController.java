package com.tomislav.kralj.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.model.service.PersonDTO;
import com.tomislav.kralj.model.service.converter.PersonConverter;
import com.tomislav.kralj.model.service.metadata.DataWrapper;
import com.tomislav.kralj.model.service.metadata.Page;
import com.tomislav.kralj.repository.PersonRepository;
import com.tomislav.kralj.utility.PersonUtility;


@RestController
@RequestMapping("/person")
public class PersonController {
	public static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
	
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonUtility personUtility;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Resource<DataWrapper<PersonDTO>> getPersonList(){
		LOGGER.info("Get person list.");
		
		List<PersonDTO> result = new ArrayList<>();
		List<Person> personList = personRepository.findAll();
		if(personList == null || personList.size() == 0)
			LOGGER.info("Query returned no results!");
		
		personUtility.sort(personList);
		for(Person person : personList)
			result.add(PersonConverter.toDTO(person));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Resource<DataWrapper<PersonDTO>> getPersonList(@RequestBody Page page){
		LOGGER.info("Get person list.");
		
		List<PersonDTO> result = new ArrayList<>();
		List<Person> personList = personRepository.findAll();
		if(personList == null || personList.size() == 0)
			LOGGER.info("Query returned no results!");
		
		personList = personUtility.filter(personList, page.getFilters());
		personUtility.sort(personList, page.getSortField(), page.getSortDirection());
		for(Person person : personList)
			result.add(PersonConverter.toDTO(person));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<PersonDTO> getPerson(@PathVariable Integer id){
		LOGGER.info("Get person with id " + id + ".");
		
		Person person = personRepository.getOne(id);
		if(person == null) {
			LOGGER.error("Person with id " + id + " was not found!");
			return new ResponseEntity<PersonDTO>(new PersonDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		PersonDTO result = PersonConverter.toDTO(person);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> newPerson(@RequestBody PersonDTO personDTO){
		LOGGER.info("Add new person.");
		
		Person person = PersonConverter.toData(personDTO, personRepository);
		
		personRepository.save(person);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable Integer id){
		LOGGER.info("Update person with id " + id + ".");
		
		personDTO.setPersonId(id);
		Person person = PersonConverter.toData(personDTO, personRepository);
		
		personRepository.save(person);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deletePerson(@PathVariable Integer id){
		LOGGER.info("Delete person with id " + id + ".");
		
		personRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
