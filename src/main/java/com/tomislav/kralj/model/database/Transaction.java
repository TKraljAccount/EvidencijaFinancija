package com.tomislav.kralj.model.database;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class Transaction implements DatabaseObject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer transactionId;
	@NotNull
	private Double amount;
	private String description;
	private Date date;
	@NotNull
	@ManyToOne
	TransactionType transactionType;
	@OneToMany
	List<Payment> payments;
	@OneToMany
	List<Income> incomes;

}
