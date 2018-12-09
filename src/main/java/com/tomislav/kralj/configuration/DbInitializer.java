package com.tomislav.kralj.configuration;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.repository.PersonRepository;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
	public static final Logger LOGGER = LoggerFactory.getLogger(DbInitializer.class);

	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	EvidencijeSettings settings;

	
    @Override
    public void run(String... strings) throws Exception {
    	LOGGER.info("Initializing database...");

        this.personRepository.deleteAll();
        Person person3 = new Person();
        person3.setFirstName("test3");
        person3.setLastName("test3");
        Person person1 = new Person();
        person1.setFirstName("test");
        person1.setLastName("test");
        Person person2 = new Person();
        person2.setFirstName("test2");
        person2.setLastName("test2");
        List<Person> persons = new ArrayList<>();
        persons.add(person3);
        persons.add(person1);
        persons.add(person2);
        this.personRepository.saveAll(persons);
        
        LOGGER.info("Database has been initialized.");
    }
}
