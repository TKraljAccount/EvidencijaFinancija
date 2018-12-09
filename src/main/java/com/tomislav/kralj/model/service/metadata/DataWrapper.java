package com.tomislav.kralj.model.service.metadata;

import lombok.Data;


@Data
public class DataWrapper<T> {
	
	private T data;
	private Page page = new Page();
	
	
	public DataWrapper(T data) {
		this.data = data;
	}

}
