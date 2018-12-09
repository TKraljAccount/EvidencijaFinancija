package com.tomislav.kralj.model.service.metadata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class Page {
	@Value("${evidencije.page_size}")
	public static final int DEFAULT_PAGE_SIZE = 10;

	private int number = 1;
	private int size = DEFAULT_PAGE_SIZE;
	private int totalPages;
	private int totalElem;
	private Map<String, String> filters;
	private String sortField;
	private String sortDirection;
	
}
