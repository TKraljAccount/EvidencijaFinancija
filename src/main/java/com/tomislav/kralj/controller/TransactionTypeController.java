package com.tomislav.kralj.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tomislav.kralj.model.database.TransactionType;
import com.tomislav.kralj.model.service.TransactionTypeDTO;
import com.tomislav.kralj.model.service.converter.TransactionTypeConverter;
import com.tomislav.kralj.model.service.metadata.DataWrapper;
import com.tomislav.kralj.repository.TransactionTypeRepository;



@RestController
@RequestMapping("/transactionType")
public class TransactionTypeController {
	public static final Logger LOGGER = LoggerFactory.getLogger(TransactionTypeController.class);
	
	
	@Autowired
	TransactionTypeRepository transactionTypeRepository;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Resource<DataWrapper<List<TransactionTypeDTO>>> getTransactionTypeList(){
		LOGGER.info("Get transactionType list.");
		
		List<TransactionTypeDTO> result = new ArrayList<>();
		List<TransactionType> transactionTypeList = transactionTypeRepository.findAll();
		if(transactionTypeList == null || transactionTypeList.size() == 0)
			LOGGER.info("Query returned no results!");
		
		for(TransactionType transactionType : transactionTypeList)
			result.add(TransactionTypeConverter.toDTO(transactionType));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> newTransactionType(@RequestBody TransactionTypeDTO transactionTypeDTO){
		LOGGER.info("Add new transactionType.");
		
		TransactionType transactionType = TransactionTypeConverter.toData(transactionTypeDTO, transactionTypeRepository);
		
		transactionTypeRepository.save(transactionType);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateTransactionType(@RequestBody TransactionTypeDTO transactionTypeDTO, @PathVariable Integer id){
		LOGGER.info("Update transactionType with id " + id + ".");
		
		transactionTypeDTO.setTypeId(id);
		TransactionType transactionType = TransactionTypeConverter.toData(transactionTypeDTO, transactionTypeRepository);
		
		transactionTypeRepository.save(transactionType);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteTransactionType(@PathVariable Integer id){
		LOGGER.info("Delete transactionType with id " + id + ".");
		
		transactionTypeRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
