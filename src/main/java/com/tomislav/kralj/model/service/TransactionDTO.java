package com.tomislav.kralj.model.service;

import lombok.Data;

@Data
public class TransactionDTO implements ServiceObject {
	
	private Integer transactionId;
	private Double amount;
	private String description;
	private String date;
	private String type;

}
