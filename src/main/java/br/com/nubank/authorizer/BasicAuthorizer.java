package br.com.nubank.authorizer;

import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Transaction;
import br.com.nubank.authorizer.models.TransactionResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasicAuthorizer implements Authorizer {

    private List<Transaction> transactionsHistory;

    BasicAuthorizer() {
        this.transactionsHistory = new ArrayList<>();
    }

    @Override
    public TransactionResult authorize(Account account, Transaction transaction) {
        getTransactionsHistory().add(transaction);
        return new TransactionResult(account, Collections.emptyList());
    }

    public List<Transaction> getTransactionsHistory() {
        return transactionsHistory;
    }
}
