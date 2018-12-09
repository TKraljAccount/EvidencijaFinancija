package com.tomislav.kralj.model.database;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public abstract class SubTransaction implements DatabaseObject {
	
	@NotNull
	private Double amount;
	private String description;
	@NotNull
	@ManyToOne
	Person person;
	@NotNull
	@ManyToOne
	Transaction transaction;
	
	public abstract Integer getId();
	
	public abstract void setId(Integer id);
	

}
