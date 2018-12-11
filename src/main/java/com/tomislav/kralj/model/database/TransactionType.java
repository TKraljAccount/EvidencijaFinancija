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

/**
 * This class represents a transaction type.
 *
 * It is useful for filtering transactions and deciding whether there should be any constraints related to a
 * certain type of transaction.
 * E.g. if a person wants to borrow money from someone it is saved as a transaction with type "Borrowing".
 * 	As long as each person's sum of payments related to this transaction isn't equal to their sum of income,
 * 	the money is not returned.
 */

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
