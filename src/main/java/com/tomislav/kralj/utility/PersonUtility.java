package com.tomislav.kralj.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomislav.kralj.configuration.EvidencijeSettings;
import com.tomislav.kralj.model.database.DataComparator;
import com.tomislav.kralj.model.database.Person;


@Component
public class PersonUtility implements ControllerUtility<Person> {
	public static final Logger LOGGER = LoggerFactory.getLogger(PersonUtility.class);
	
	@Autowired
	EvidencijeSettings settings;
	
	
	@Override
	public void sort(List<Person> personList) {
		sort(personList, null, null);
	}

	public void sort(List<Person> personList, String sortField, String sortDirection) {
		LOGGER.info("Sort filter list.");
		
		if(sortField == null)
			sortField = settings.getSortField().getPerson();
		if(sortDirection == null)
			sortDirection = settings.getSortDirection().getPerson();
		
		
		Comparator<Person> comparator = new DataComparator<>(sortField);
		

		if(sortDirection.equals(ASCENDING))
			personList.sort(comparator);
		else if(sortDirection.equals(DESCENDING))
			personList.sort(comparator.reversed());
	}
	
	
	public List<Person> filter(List<Person> personList, Map<String, String> filters){
		LOGGER.info("Filter person list.");
		
		List<Person> filteredList = new ArrayList<>();
		
		if(filters == null)
			return filteredList;
		
		personLoop: for(Person person : personList) {
			for(Map.Entry<String, String> entry : filters.entrySet()) {
				if(entry.getKey().equals("firstName") && 
						(person.getFirstName() == null || 
						!person.getFirstName().contains(entry.getValue())))
					continue personLoop;
				if(entry.getKey().equals("lastName") && 
						(person.getLastName() == null || 
						!person.getLastName().contains(entry.getValue())))
					continue personLoop;
				if(entry.getKey().equals("username") && 
						(person.getUsername() == null || 
						!person.getUsername().contains(entry.getValue())))
					continue personLoop;
				if(entry.getKey().equals("email") && 
						(person.getEmail() == null || 
						!person.getEmail().contains(entry.getValue())))
					continue personLoop;
			}
			
			filteredList.add(person);
		}
		
		return filteredList;
	}
	
	
}
