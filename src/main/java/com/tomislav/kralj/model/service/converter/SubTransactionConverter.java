package com.tomislav.kralj.model.service.converter;

import com.tomislav.kralj.model.database.Income;
import com.tomislav.kralj.model.database.Payment;
import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.model.database.SubTransaction;
import com.tomislav.kralj.model.database.Transaction;
import com.tomislav.kralj.model.service.SubTransactionDTO;
import com.tomislav.kralj.repository.IncomeRepository;
import com.tomislav.kralj.repository.PaymentRepository;
import com.tomislav.kralj.repository.PersonRepository;
import com.tomislav.kralj.repository.TransactionRepository;

public abstract class SubTransactionConverter implements Converter {
	
	
	public static SubTransactionDTO toDTO(SubTransaction data) {
		SubTransactionDTO dto = new SubTransactionDTO();
		
		dto.setId(data.getId());
		dto.setAmount(data.getAmount());
		dto.setDescription(data.getDescription());
		if(data.getPerson() != null)
			dto.setPersonId(data.getPerson().getPersonId());
		if(data.getTransaction() != null)
			dto.setTransactionId(data.getTransaction().getTransactionId());
		
		if(data instanceof Income)
			dto.setIncome(true);
		else
			dto.setIncome(false);
		
		return dto;
	}
	
	public static SubTransaction toData(SubTransactionDTO dto, IncomeRepository incomeRepository, PaymentRepository paymentRepository,
			PersonRepository personRepository, TransactionRepository transactionRepository) {
		SubTransaction data;
		if(dto.isIncome())
			data = incomeRepository.findById(dto.getId()).orElse(new Income());
		else
			data = paymentRepository.findById(dto.getId()).orElse(new Payment());
		
		Person person = personRepository.getOne(dto.getPersonId());
		Transaction transaction = transactionRepository.getOne(dto.getTransactionId());
		
		data.setId(dto.getId());
		data.setAmount(dto.getAmount());
		data.setDescription(dto.getDescription());
		data.setPerson(person);
		data.setTransaction(transaction);
		
		return data;
	}

}
