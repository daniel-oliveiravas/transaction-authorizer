package br.com.nubank.authorizer.utils;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestsHelper {

    public static Transaction createTransaction(String merchant, LocalDateTime time, Integer amount) {
        return new Transaction(merchant, amount, LocalDateTime.from(time));
    }

    public static Account createAccount(Integer availableLimit, Boolean activeCard) {
        return new Account(availableLimit, activeCard);
    }

    public static List<Transaction> createTransactionHistory(String merchant, int transactionsQuantity, Integer amount) {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);
        List<Transaction> transactionsHistory = new ArrayList<>();
        for (int i = 0; i < transactionsQuantity; i++) {
            transactionsHistory.add(TestsHelper.createTransaction(merchant, dateTime.plusSeconds(i + 1), amount));
        }

        return transactionsHistory;
    }

}
