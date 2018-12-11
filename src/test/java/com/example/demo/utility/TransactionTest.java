package com.example.demo.utility;

import com.tomislav.kralj.model.database.SubTransaction;
import com.tomislav.kralj.model.database.Transaction;
import com.tomislav.kralj.model.database.TransactionType;
import com.tomislav.kralj.utility.TransactionUtility;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    List<Transaction> list = new ArrayList<>();
    TransactionUtility utility;

    @Before
    public void initList() {
        utility = new TransactionUtility();

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
        transaction2.setDate(new Date(new java.util.Date().getTime() + 1000+60+60+24));

        list.add(transaction1);
        list.add(transaction2);
    }

    @Test
    public void testFilter() {
        Map<String, String> filters = new HashMap<>();
        filters.put("type", "Borrowing");

        list = utility.filter(list, filters);

        assertEquals(1, list.size());
    }

    @Test
    public void testSort() {
        utility.sort(list, "date", "asc");
        assertEquals(new Double(11.1), list.get(0).getAmount());

        utility.sort(list, "date", "dsc");
        assertEquals(new Double(100.), list.get(0).getAmount());
    }
}
