package br.com.nubank.models;

public class TransactionAuthorization {

    private Account account;
    private Transaction transaction;

    public TransactionAuthorization(Account account, Transaction transaction) {
        this.account = account;
        this.transaction = transaction;
    }

    public Account getAccount() {
        return account;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}
