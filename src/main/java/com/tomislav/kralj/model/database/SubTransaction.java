package com.tomislav.kralj.model.database;


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
