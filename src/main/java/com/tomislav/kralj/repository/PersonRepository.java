package com.tomislav.kralj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomislav.kralj.model.database.Person;

public interface PersonRepository extends JpaRepository<Person, Integer> {
	

}
