package com.tomislav.kralj.model.service.converter;

import com.tomislav.kralj.model.database.TransactionType;
import com.tomislav.kralj.model.service.TransactionTypeDTO;
import com.tomislav.kralj.repository.TransactionTypeRepository;

public abstract class TransactionTypeConverter implements Converter {

	
	public static TransactionTypeDTO toDTO(TransactionType data) {
		TransactionTypeDTO dto = new TransactionTypeDTO();
		
		dto.setTypeId(data.getTypeId());
		dto.setName(data.getName());
		
		return dto;
	}
	
	public static TransactionType toData(TransactionTypeDTO dto, TransactionTypeRepository transactionTypeRepository) {
		TransactionType data = transactionTypeRepository.findById(dto.getTypeId()).orElse(new TransactionType());
		
		data.setTypeId(dto.getTypeId());
		data.setName(data.getName());
		
		return data;
	}
	
	
}
