package com.tomislav.kralj.model.service;

import lombok.Data;


@Data
public class TransactionTypeDTO implements ServiceObject {
	
	private Integer typeId;
	private String name;

}
