package com.tomislav.kralj.utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tomislav.kralj.configuration.EvidencijeSettings;
import com.tomislav.kralj.model.database.DataComparator;
import com.tomislav.kralj.model.database.Income;
import com.tomislav.kralj.model.database.SubTransaction;


@Component
public class SubTransactionUtility implements ControllerUtility<SubTransaction>{
	public static final Logger LOGGER = LoggerFactory.getLogger(SubTransactionUtility.class);
	
	@Autowired
	EvidencijeSettings settings;
	
	
	@Override
	public void sort(List<SubTransaction> list) {
		sort(list, null, null);
	}

	public void sort(List<SubTransaction> list, String sortField, String sortDirection) {
		LOGGER.info("Sort filter list.");
		
		if(sortField == null)
			sortField = settings.getSortField().getSubTransaction();
		if(sortDirection == null)
			sortDirection = settings.getSortDirection().getSubTransaction();
		
		
		Comparator<SubTransaction> comparator = new DataComparator<>(sortField);
		

		if(sortDirection.equals(ASCENDING))
			list.sort(comparator);
		else if(sortDirection.equals(DESCENDING))
			list.sort(comparator.reversed());
	}
	
	
	public List<SubTransaction> filter(List<SubTransaction> list, Map<String, String> filters){
		LOGGER.info("Filter person list.");
		
		List<SubTransaction> filteredList = new ArrayList<>();
		
		if(filters == null)
			return filteredList;
		
		filterLoop: for(SubTransaction item : list) {
			for(Map.Entry<String, String> entry : filters.entrySet()) {
				if(entry.getKey().equals("amount") && 
						(item.getAmount() == null || 
						!item.getAmount().equals(Double.parseDouble(entry.getValue()))))
					continue filterLoop;
				if(entry.getKey().equals("description") && 
						(item.getDescription() == null || 
						!item.getDescription().contains(entry.getValue())))
					continue filterLoop;
				if(entry.getKey().equals("isIncome") && 
						(item instanceof Income))
					continue filterLoop;
				if(entry.getKey().equals("personId") && 
						(item.getPerson() == null || 
						!item.getPerson().getPersonId().equals(Integer.parseInt(entry.getValue()))))
					continue filterLoop;
				if(entry.getKey().equals("transactionId") && 
						(item.getTransaction() == null || 
						!item.getTransaction().getTransactionId().equals(Integer.parseInt(entry.getValue()))))
					continue filterLoop;
			}
			
			filteredList.add(item);
		}
		
		return filteredList;
	}

}
