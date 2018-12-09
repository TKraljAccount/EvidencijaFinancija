package com.tomislav.kralj.model.database;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class TransactionType implements DatabaseObject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer typeId;
	@NotNull
	@Column(unique=true)
	private String name;
	@OneToMany
	List<Transaction> transactions;

}
