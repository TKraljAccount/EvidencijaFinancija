package com.tomislav.kralj.model.service.metadata;

import java.util.List;

import lombok.Data;


@Data
public class DataWrapper<T> {
	
	private List<T> data;
	private Page page = new Page();

	
	public DataWrapper(List<T> data) {
		this(data, new Page());
	}
	
	public DataWrapper(List<T> data, Page page) {
		int start;
		int end;
		
		start = (page.getNumber()-1)*page.getSize();
		end = start + page.getSize();
		
		this.page = page;
		if(end < data.size())
			this.data = data.subList(start, end);
		else
			this.data = data.subList(start, data.size());
		
		int totalPages;
		totalPages = data.size()/page.getSize();
		if(totalPages < (double)data.size()/page.getSize())
			totalPages++;
		
		this.page.setTotalElem(data.size());
		this.page.setTotalPages(totalPages);
	}
	
	

}
