package com.tomislav.kralj.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tomislav.kralj.model.database.Transaction;
import com.tomislav.kralj.model.service.TransactionDTO;
import com.tomislav.kralj.model.service.converter.TransactionConverter;
import com.tomislav.kralj.model.service.metadata.DataWrapper;
import com.tomislav.kralj.model.service.metadata.Page;
import com.tomislav.kralj.repository.TransactionRepository;
import com.tomislav.kralj.repository.TransactionTypeRepository;
import com.tomislav.kralj.utility.TransactionUtility;


@RestController
@RequestMapping("/transaction")
public class TransactionController {
	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	

	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	TransactionTypeRepository typeRepository;
	
	@Autowired
	TransactionUtility transactionUtility;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Resource<DataWrapper<List<TransactionDTO>>> getTransactionList(){
		LOGGER.info("Get transaction list.");
		
		List<TransactionDTO> result = new ArrayList<>();
		List<Transaction> transactionList = transactionRepository.findAll();
		if(transactionList == null || transactionList.size() == 0)
			LOGGER.info("Query returned no results!");
		
		transactionUtility.sort(transactionList);
		for(Transaction transaction : transactionList)
			result.add(TransactionConverter.toDTO(transaction));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Resource<DataWrapper<List<TransactionDTO>>> getTransactionList(@RequestBody Page page){
		LOGGER.info("Get transaction list.");
		
		List<TransactionDTO> result = new ArrayList<>();
		List<Transaction> transactionList = transactionRepository.findAll();
		if(transactionList == null || transactionList.size() == 0)
			LOGGER.info("Query returned no results!");
		
		transactionList = transactionUtility.filter(transactionList, page.getFilters());
		transactionUtility.sort(transactionList, page.getSortField(), page.getSortDirection());
		for(Transaction transaction : transactionList)
			result.add(TransactionConverter.toDTO(transaction));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Resource<TransactionDTO> getTransaction(@PathVariable Integer id) throws NotFoundException{
		LOGGER.info("Get transaction with id " + id + ".");
		
		Transaction transaction = transactionRepository.getOne(id);
		if(transaction == null) {
			LOGGER.error("Transaction with id " + id + " was not found!");
			throw new NotFoundException();
		}
		
		TransactionDTO result = TransactionConverter.toDTO(transaction);
		
		return new Resource<>(result);
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> newTransaction(@RequestBody TransactionDTO transactionDTO){
		LOGGER.info("Add new transaction.");
		
		Transaction transaction;
		try {
			transaction = TransactionConverter.toData(transactionDTO, transactionRepository, typeRepository);
		
			transactionRepository.save(transaction);
		} catch (ParseException e) {
			LOGGER.error("Wrong date format: " + transactionDTO.getDate() + "!");
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateTransaction(@RequestBody TransactionDTO transactionDTO, @PathVariable Integer id){
		LOGGER.info("Update transaction with id " + id + ".");
		
		transactionDTO.setTransactionId(id);
		Transaction transaction;
		try {
			transaction = TransactionConverter.toData(transactionDTO, transactionRepository, typeRepository);
		
			transactionRepository.save(transaction);
		} catch (ParseException e) {
			LOGGER.error("Wrong date format: " + transactionDTO.getDate() + "!");
			LOGGER.error(e.getMessage());
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable Integer id){
		LOGGER.info("Delete transaction with id " + id + ".");
		
		transactionRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
