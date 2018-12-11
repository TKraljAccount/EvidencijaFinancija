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

import com.tomislav.kralj.model.database.Income;
import com.tomislav.kralj.model.database.Payment;
import com.tomislav.kralj.model.database.SubTransaction;
import com.tomislav.kralj.model.service.SubTransactionDTO;
import com.tomislav.kralj.model.service.converter.SubTransactionConverter;
import com.tomislav.kralj.model.service.metadata.DataWrapper;
import com.tomislav.kralj.model.service.metadata.Page;
import com.tomislav.kralj.repository.IncomeRepository;
import com.tomislav.kralj.repository.PaymentRepository;
import com.tomislav.kralj.repository.PersonRepository;
import com.tomislav.kralj.repository.TransactionRepository;
import com.tomislav.kralj.utility.SubTransactionUtility;


@RestController
@RequestMapping("/payment")
public class SubTransactionController {
	public static final Logger LOGGER = LoggerFactory.getLogger(SubTransactionController.class);
	

	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	PersonRepository personRepository;
	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	SubTransactionUtility subTransactionUtility;

	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Resource<DataWrapper<SubTransactionDTO>> getPaymentList(){
		LOGGER.info("Get payment/income list.");
		
		List<SubTransactionDTO> result = new ArrayList<>();
		List<SubTransaction> list = new ArrayList<>();
		List<? extends SubTransaction> paymentList = paymentRepository.findAll();
		List<? extends SubTransaction> incomeList = incomeRepository.findAll();
		list.addAll(paymentList);
		list.addAll(incomeList);

		if(list == null || list.size() == 0)
			LOGGER.info("Query returned no results!");
		
		subTransactionUtility.sort(list);
		for(SubTransaction elem : list)
			result.add(SubTransactionConverter.toDTO(elem));
		
		return new Resource<>(new DataWrapper<>(result));
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public Resource<DataWrapper<SubTransactionDTO>> getPaymentList(@RequestBody Page page){
		LOGGER.info("Get payment/income list.");
		
		List<SubTransactionDTO> result = new ArrayList<>();
		List<SubTransaction> list = new ArrayList<>();
		List<? extends SubTransaction> paymentList = paymentRepository.findAll();
		List<? extends SubTransaction> incomeList = incomeRepository.findAll();
		list.addAll(paymentList);
		list.addAll(incomeList);
		
		if(list == null || list.size() == 0)
			LOGGER.info("Query returned no results!");
		
		list = subTransactionUtility.filter(list, page.getFilters());
		subTransactionUtility.sort(list, page.getSortField(), page.getSortDirection());
		for(SubTransaction elem : list)
			result.add(SubTransactionConverter.toDTO(elem));
		
		return new Resource<>(new DataWrapper<>(result, page));
	}
	
	
	@RequestMapping(value="/income/{id}", method=RequestMethod.GET)
	public ResponseEntity<SubTransactionDTO> getIncome(@PathVariable Integer id){
		LOGGER.info("Get income with id " + id + ".");
		
		SubTransaction elem = incomeRepository.getOne(id);
		if(elem == null) {
			LOGGER.error("Income with id " + id + " was not found!");
			return new ResponseEntity<>(new SubTransactionDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		SubTransactionDTO result = SubTransactionConverter.toDTO(elem);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@RequestMapping(value="/payment/{id}", method=RequestMethod.GET)
	public ResponseEntity<SubTransactionDTO> getPayment(@PathVariable Integer id){
		LOGGER.info("Get payment with id " + id + ".");
		
		SubTransaction elem = paymentRepository.getOne(id);
		if(elem == null) {
			LOGGER.error("Payment with id " + id + " was not found!");
			return new ResponseEntity<>(new SubTransactionDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		SubTransactionDTO result = SubTransactionConverter.toDTO(elem);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/income/new", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> newIncome(@RequestBody SubTransactionDTO dto){
		LOGGER.info("Add new income.");
		
		SubTransaction income = SubTransactionConverter.toData(dto, incomeRepository, paymentRepository, personRepository, transactionRepository);
		
		incomeRepository.save((Income) income);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/payment/new", method=RequestMethod.POST)
	public ResponseEntity<HttpStatus> newPayment(@RequestBody SubTransactionDTO dto){
		LOGGER.info("Add new payment.");
		
		SubTransaction payment = SubTransactionConverter.toData(dto, incomeRepository, paymentRepository, personRepository, transactionRepository);
		
		paymentRepository.save((Payment) payment);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@RequestMapping(value="/income/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updateIncome(@RequestBody SubTransactionDTO dto, @PathVariable Integer id){
		LOGGER.info("Update income with id " + id + ".");
		
		dto.setId(id);
		SubTransaction result = SubTransactionConverter.toData(dto, incomeRepository, paymentRepository, personRepository, transactionRepository);
		
		incomeRepository.save((Income) result);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/payment/update/{id}", method=RequestMethod.PUT)
	public ResponseEntity<HttpStatus> updatePayment(@RequestBody SubTransactionDTO dto, @PathVariable Integer id){
		LOGGER.info("Update payment with id " + id + ".");
		
		dto.setId(id);
		SubTransaction result = SubTransactionConverter.toData(dto, incomeRepository, paymentRepository, personRepository, transactionRepository);
		
		paymentRepository.save((Payment) result);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	

	@RequestMapping(value="/income/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deleteIncome(@PathVariable Integer id){
		LOGGER.info("Delete income with id " + id + ".");
		
		incomeRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/payment/delete/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> deletePayment(@PathVariable Integer id){
		LOGGER.info("Delete payment with id " + id + ".");
		
		paymentRepository.deleteById(id);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
