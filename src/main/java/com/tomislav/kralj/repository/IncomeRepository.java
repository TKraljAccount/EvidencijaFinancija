package com.tomislav.kralj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomislav.kralj.model.database.Income;

public interface IncomeRepository extends JpaRepository<Income, Integer> {

}
