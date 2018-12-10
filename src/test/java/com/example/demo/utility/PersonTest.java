package com.example.demo.utility;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.utility.PersonUtility;

public class PersonTest {
	
    List<Person> persons = new ArrayList<>();
    PersonUtility personUtility;
	
	@Before
	public void initList() {
		personUtility = new PersonUtility();
		
        Person person1 = new Person();
        person1.setFirstName("Ante");
        person1.setLastName("Antić");
        Person person2 = new Person();
        person2.setFirstName("Branimir");
        person2.setLastName("Branimirović");
        persons.add(person2);
        persons.add(person1);
	}

	@Test
	public void testFilter() {
        Map<String, String> filters = new HashMap<>();
        filters.put("firstName", "An");
        
        persons = personUtility.filter(persons,  filters);
        
        assertEquals(1, persons.size());
	}
	
	@Test
	public void testSort() {
		personUtility.sort(persons, "firstName", "asc");
		assertEquals("Ante", persons.get(0).getFirstName());
		
		personUtility.sort(persons, "firstName", "dsc");
		assertEquals("Branimir", persons.get(0).getFirstName());
	}

}
