package com.tomislav.kralj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomislav.kralj.model.database.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

}
