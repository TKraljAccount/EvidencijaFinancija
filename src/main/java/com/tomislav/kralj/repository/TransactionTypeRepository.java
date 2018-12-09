package com.tomislav.kralj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomislav.kralj.model.database.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Integer> {
	
	Optional<TransactionType> findByName(String name);

}
