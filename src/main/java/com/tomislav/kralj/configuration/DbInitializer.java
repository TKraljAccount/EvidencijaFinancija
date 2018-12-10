package com.tomislav.kralj.configuration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.tomislav.kralj.model.database.Income;
import com.tomislav.kralj.model.database.Payment;
import com.tomislav.kralj.model.database.Person;
import com.tomislav.kralj.model.database.Transaction;
import com.tomislav.kralj.model.database.TransactionType;
import com.tomislav.kralj.repository.IncomeRepository;
import com.tomislav.kralj.repository.PaymentRepository;
import com.tomislav.kralj.repository.PersonRepository;
import com.tomislav.kralj.repository.TransactionRepository;
import com.tomislav.kralj.repository.TransactionTypeRepository;


@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitializer implements CommandLineRunner {
	public static final Logger LOGGER = LoggerFactory.getLogger(DbInitializer.class);

	@Autowired
	PersonRepository personRepository;
	@Autowired
	TransactionRepository transactionRepository;
	@Autowired
	TransactionTypeRepository transactionTypeRepository;
	@Autowired
	IncomeRepository incomeRepository;
	@Autowired
	PaymentRepository paymentRepository;
	
	@Autowired
	EvidencijeSettings settings;

	
    @Override
    public void run(String... strings) throws Exception {
    	LOGGER.info("Initializing database...");
    	
    	// Initialize Person table.
        this.personRepository.deleteAll();
        Person person3 = new Person();
        person3.setFirstName("Dario");
        person3.setLastName("Darić");
        Person person1 = new Person();
        person1.setFirstName("Ante");
        person1.setLastName("Antić");
        Person person2 = new Person();
        person2.setFirstName("Branimir");
        person2.setLastName("Branimirović");
        List<Person> persons = new ArrayList<>();
        persons.add(person3);
        persons.add(person1);
        persons.add(person2);
        this.personRepository.saveAll(persons);
        this.personRepository.flush();
        
        // Initialize TransactionType table.
        this.transactionTypeRepository.deleteAll();
        TransactionType type1 = new TransactionType();
        type1.setName("Borrowing");
        TransactionType type2 = new TransactionType();
        type2.setName("GroupIncome");
        TransactionType type3 = new TransactionType();
        type3.setName("GroupExpense");
        List<TransactionType> typeList = new ArrayList<>();
        typeList.add(type1);
        typeList.add(type2);
        typeList.add(type3);
        this.transactionTypeRepository.saveAll(typeList);
        
        // Initialize Transaction table.
        this.transactionRepository.deleteAll();
        Transaction transaction = new Transaction();
        transaction.setAmount(new Double(11.1));
        transaction.setDate(new Date( new java.util.Date().getTime() ));
        transaction.setTransactionType(type1);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new Double(1.1));
        transaction2.setDate(new Date( new java.util.Date().getTime() + 1000*60*60*24 ));
        transaction2.setTransactionType(type3);
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        this.transactionRepository.saveAll(transactionList);
        
        // Initialize payment table.
        this.paymentRepository.deleteAll();
        Payment payment1 = new Payment();
        payment1.setAmount(new Double(11.1));
        payment1.setTransaction(transaction);
        payment1.setPerson(person1);
        Payment payment2 = new Payment();
        payment2.setAmount(new Double(0.4));
        payment2.setTransaction(transaction2);
        payment2.setPerson(person2);
        Payment payment3 = new Payment();
        payment3.setAmount(new Double(0.7));
        payment3.setTransaction(transaction2);
        payment3.setPerson(person1);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);
        paymentList.add(payment3);
        this.paymentRepository.saveAll(paymentList);
        
        // Initialize Income table.
        this.incomeRepository.deleteAll();
        Income income = new Income();
        income.setAmount(11.1);
        income.setTransaction(transaction);
        income.setPerson(person2);
        this.incomeRepository.save(income);
        
        LOGGER.info("Database has been initialized.");
    }
}
