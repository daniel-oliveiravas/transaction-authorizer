package br.com.nubank.models;

import java.util.List;

public class TransactionAuthorization {

    private Account account;
    private Transaction currentTransaction;
    private List<Transaction> transactionsHistory;

    public TransactionAuthorization(Account account, Transaction currentTransaction, List<Transaction> transactionsHistory) {
        this.account = account;
        this.currentTransaction = currentTransaction;
        this.transactionsHistory = transactionsHistory;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public List<Transaction> getTransactionsHistory() {
        return transactionsHistory;
    }
}
