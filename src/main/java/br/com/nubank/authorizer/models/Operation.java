package br.com.nubank.authorizer.models;

import java.util.List;

public class Operation {

    private Account account;
    private Transaction currentTransaction;
    private List<Transaction> transactionsHistory;

    public Operation(Account account, Transaction currentTransaction, List<Transaction> transactionsHistory) {
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
