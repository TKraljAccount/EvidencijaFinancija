package com.tomislav.kralj.utility;

import java.util.List;
import java.util.Map;

import com.tomislav.kralj.model.database.DatabaseObject;


public interface ControllerUtility<T extends DatabaseObject> {
	
	String ASCENDING = "asc";
	String DESCENDING = "dsc";
	

	void sort(List<T> list);
	void sort(List<T> list, String sortField, String sortDirection);
	
	List<T> filter(List<T> list, Map<String, String> filters);

}
