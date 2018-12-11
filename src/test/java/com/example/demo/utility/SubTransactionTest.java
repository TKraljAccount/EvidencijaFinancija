package com.example.demo.utility;

import com.tomislav.kralj.model.database.*;
import com.tomislav.kralj.utility.SubTransactionUtility;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SubTransactionTest {

    List<SubTransaction> list = new ArrayList<>();
    SubTransactionUtility utility;

    @Before
    public void initList() {
        utility = new SubTransactionUtility();

        Person person1 = new Person();
        person1.setFirstName("Ante");
        person1.setLastName("Antić");
        Person person2 = new Person();
        person2.setFirstName("Branimir");
        person2.setLastName("Branimirović");

        TransactionType type1 = new TransactionType();
        type1.setName("Borrowing");
        TransactionType type2 = new TransactionType();
        type2.setName("GroupExpense");

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(11.1);
        transaction1.setTransactionType(type1);
        transaction1.setDate(new Date(new java.util.Date().getTime()));
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(100.);
        transaction2.setTransactionType(type2);
        transaction2.setDate(new Date(new java.util.Date().getTime()));

        Payment payment1 = new Payment();
        payment1.setPerson(person1);
        payment1.setAmount(11.1);
        payment1.setTransaction(transaction1);
        Payment payment2 = new Payment();
        payment2.setPerson(person2);
        payment2.setAmount(100.);
        payment2.setTransaction(transaction2);
        Income income1 = new Income();
        income1.setPerson(person1);
        income1.setAmount(11.1);
        income1.setTransaction(transaction1);

        list.add(payment1);
        list.add(payment2);
        list.add(income1);
    }

    @Test
    public void testFilter() {
        Map<String, String> filters = new HashMap<>();
        filters.put("isIncome", "");

        list = utility.filter(list, filters);

        assertEquals(1, list.size());
    }

    @Test
    public void testSort() {
        utility.sort(list, "amount", "asc");
        assertEquals(new Double(11.1), list.get(0).getAmount());

        utility.sort(list, "amount", "dsc");
        assertEquals(new Double(100.), list.get(0).getAmount());
    }

}
