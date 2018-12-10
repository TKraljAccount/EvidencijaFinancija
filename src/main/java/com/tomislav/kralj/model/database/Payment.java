package com.tomislav.kralj.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Entity
@Data
public class Payment implements SubTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer paymentId;
	
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
		return paymentId;
	}

	@Override
	public void setId(Integer id) {
		paymentId = id;
	}
	

}
