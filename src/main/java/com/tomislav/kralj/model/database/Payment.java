package com.tomislav.kralj.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data
public class Payment extends SubTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer paymentId;

	@Override
	public Integer getId() {
		return paymentId;
	}

	@Override
	public void setId(Integer id) {
		paymentId = id;
	}
	

}
