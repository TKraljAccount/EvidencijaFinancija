package com.tomislav.kralj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomislav.kralj.model.database.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
