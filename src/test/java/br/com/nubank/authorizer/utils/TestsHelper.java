package br.com.nubank.authorizer.utils;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionAuthorization;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestsHelper {

    public static Transaction createTransaction(String merchant, LocalDateTime time, Integer amount) {
        return new Transaction(merchant, amount, LocalDateTime.from(time));
    }

    public static TransactionAuthorization createTransactionAuthorization(Account account, Transaction currentTransaction) {
        return new TransactionAuthorization(account, currentTransaction);
    }

    public static Account createAccount(Integer availableLimit, Boolean activeCard, List<Transaction> history) {
        Account account = new Account(availableLimit, activeCard);
        for (Transaction transaction : history) {
            account.addTransaction(transaction);
        }
        return account;
    }

    public static Account createAccount(Integer availableLimit, Boolean activeCard) {
        return createAccount(availableLimit, activeCard, Collections.emptyList());
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
