package com.tomislav.kralj.model.service.converter;

import java.sql.Date;
import java.text.ParseException;

import com.tomislav.kralj.model.database.Transaction;
import com.tomislav.kralj.model.database.TransactionType;
import com.tomislav.kralj.model.service.TransactionDTO;
import com.tomislav.kralj.repository.TransactionRepository;
import com.tomislav.kralj.repository.TransactionTypeRepository;

public abstract class TransactionConverter implements Converter {

	public static TransactionDTO toDTO(Transaction data) {
		TransactionDTO dto = new TransactionDTO();
		
		dto.setTransactionId(data.getTransactionId());
		dto.setAmount(data.getAmount());
		dto.setDescription(data.getDescription());
		if(data.getDate() != null)
			dto.setDate(dateFormat.format(data.getDate()));
		dto.setDescription(data.getDescription());
		if(data.getTransactionType() != null)
			dto.setType(data.getTransactionType().getName());
		
		return dto;
	}
	
	public static Transaction toData(TransactionDTO dto, TransactionRepository transactionRepository, TransactionTypeRepository typeRepository) throws ParseException {
		Transaction data = transactionRepository.findById(dto.getTransactionId()).orElse(new Transaction());
		TransactionType type = typeRepository.findByName(dto.getType()).orElse(null);
		
		data.setTransactionId(dto.getTransactionId());
		data.setAmount(dto.getAmount());
		if(dto.getDate() != null)
			data.setDate(new Date(dateFormat.parse(dto.getDate()).getTime()));
		data.setDescription(dto.getDescription());
		data.setTransactionType(type);
		
		return data;
	}
	
}
