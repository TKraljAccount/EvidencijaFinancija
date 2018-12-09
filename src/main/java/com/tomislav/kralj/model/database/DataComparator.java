package com.tomislav.kralj.model.database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataComparator<T extends DatabaseObject> implements Comparator<T> {
	public static final Logger LOGGER = LoggerFactory.getLogger(DataComparator.class);
	
	private String field;
	
	public DataComparator(String field) {
		this.field = field;
	}

	
	@Override
	public int compare(T o1, T o2) {
		try {
			Object value1 = o1.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1)).invoke(o1);
			Object value2 = o2.getClass().getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1)).invoke(o2);
			
			//if null
			if(value1 == null)
				return -1;
			if(value2 == null)
				return 1;
			
			if(value1 instanceof String) 
				return ((String)value1).compareTo((String) value2);
			if(value1 instanceof Double)
				return ((Double)value1).compareTo((Double) value2);
			if(value1 instanceof Date)
				return ((Date)value1).compareTo((Date)value2);
			
		} catch (IllegalArgumentException | IllegalAccessException | SecurityException | NoSuchMethodException | InvocationTargetException e) {
			LOGGER.error("Selected sort field " + field + " does not exist!");
			LOGGER.error(e.getMessage());
		} 
		
		return 0;
	}

}
