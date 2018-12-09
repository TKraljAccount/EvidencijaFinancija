package com.tomislav.kralj.model.database;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Person implements DatabaseObject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer personId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	@OneToMany
	List<Payment> payments;
	@OneToMany
	List<Income> incomes;
	

}
