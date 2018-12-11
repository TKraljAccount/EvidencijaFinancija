package com.tomislav.kralj.model.database;

/**
 * This is an interface representing individual person's payment or income.
 *
 * It is inherited by classes Payment and Income so they can use common converter and utility methods.
 */
public interface SubTransaction extends DatabaseObject {
	
	Integer getId();
	void setId(Integer id);
	
	Double getAmount();
	void setAmount(Double amount);
	
	String getDescription();
	void setDescription(String description);
	
	Person getPerson();
	void setPerson(Person person);
	
	Transaction getTransaction();
	void setTransaction(Transaction transaction);
	
	

}
