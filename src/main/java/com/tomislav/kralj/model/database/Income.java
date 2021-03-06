package com.tomislav.kralj.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;


/**
 * This class represents a person's income.
 * It has the same attributes as Payment, but is saved in a different table in the database.
 */

@Entity
@Data
public class Income implements SubTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer incomeId;
	
	@NotNull
	private Double amount;
	private String description;
	@NotNull
	@ManyToOne
	Person person;
	@NotNull
	@ManyToOne
	Transaction transaction;
	
	
	@Override
	public Integer getId() {
		return incomeId;
	}
	
	@Override
	public void setId(Integer id) {
		incomeId = id;
	}

}
