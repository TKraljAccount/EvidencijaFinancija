package com.tomislav.kralj.model.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Entity
@Data
public class Income extends SubTransaction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer incomeId;
	
	
	@Override
	public Integer getId() {
		return incomeId;
	}
	
	@Override
	public void setId(Integer id) {
		incomeId = id;
	}

}
