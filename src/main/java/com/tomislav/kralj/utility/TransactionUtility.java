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
import com.tomislav.kralj.model.database.Transaction;
import static com.tomislav.kralj.model.service.converter.Converter.dateFormat;;


@Component
public class TransactionUtility implements ControllerUtility<Transaction> {
	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionUtility.class);
	
	@Autowired
	EvidencijeSettings settings;
	
	
	@Override
	public void sort(List<Transaction> transactionList) {
		sort(transactionList, null, null);
	}

	public void sort(List<Transaction> transactionList, String sortField, String sortDirection) {
		LOGGER.info("Sort filter list.");
		
		if(sortField == null)
			sortField = settings.getSortField().getTransaction();
		if(sortDirection == null)
			sortDirection = settings.getSortDirection().getTransaction();
		
		
		Comparator<Transaction> comparator = new DataComparator<>(sortField);
		

		if(sortDirection.equals(ASCENDING))
			transactionList.sort(comparator);
		else if(sortDirection.equals(DESCENDING))
			transactionList.sort(comparator.reversed());
	}
	
	
	public List<Transaction> filter(List<Transaction> transactionList, Map<String, String> filters){
		LOGGER.info("Filter person list.");
		
		List<Transaction> filteredList = new ArrayList<>();
		
		if(filters == null)
			return filteredList;
		
		filterLoop: for(Transaction transaction : transactionList) {
			for(Map.Entry<String, String> entry : filters.entrySet()) {
				if(entry.getKey().equals("amount") && 
						(transaction.getAmount() == null || 
						!transaction.getAmount().equals(Double.parseDouble(entry.getValue()))))
					continue filterLoop;
				if(entry.getKey().equals("description") && 
						(transaction.getDescription() == null || 
						!transaction.getDescription().contains(entry.getValue())))
					continue filterLoop;
				if(entry.getKey().equals("date") && 
						(transaction.getDate() == null || 
						!dateFormat.format(transaction.getDate()).equals(entry.getValue())))
					continue filterLoop;
				if(entry.getKey().equals("type") && 
						(transaction.getTransactionType() == null || 
						!transaction.getTransactionType().getName().contains(entry.getValue())))
					continue filterLoop;
			}
			
			filteredList.add(transaction);
		}
		
		return filteredList;
	}

}
