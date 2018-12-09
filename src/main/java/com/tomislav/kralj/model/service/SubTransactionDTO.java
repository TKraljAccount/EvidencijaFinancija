package com.tomislav.kralj.model.service;

import lombok.Data;


@Data
public class SubTransactionDTO implements ServiceObject {

	private Integer id;
	private Double amount;
	private String description;
	private boolean isIncome;
	private Integer personId;
	private Integer transactionId;

}
