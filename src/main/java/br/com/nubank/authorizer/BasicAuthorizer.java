package br.com.nubank.authorizer;

import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionAuthorization;
import br.com.nubank.models.TransactionResult;

import java.util.ArrayList;
import java.util.List;

public class BasicAuthorizer implements Authorizer {

    private List<Transaction> transactionsHistory;
    private ValidatorsChain validatorsChain;

    public BasicAuthorizer(ValidatorsChain validatorsChain) {
        this.transactionsHistory = new ArrayList<>();
        this.validatorsChain = validatorsChain;
    }

    @Override
    public TransactionResult authorize(Account account, Transaction transaction) {
        TransactionAuthorization transactionAuthorization = createOperation(account, transaction);
        List<String> violations = validatorsChain.validate(transactionAuthorization);

        if (violations.isEmpty()) {
            getTransactionsHistory().add(transaction);
        }

        return new TransactionResult(account, violations);
    }

    List<Transaction> getTransactionsHistory() {
        return transactionsHistory;
    }

    private TransactionAuthorization createOperation(Account account, Transaction currentTransaction) {
        return new TransactionAuthorization(account, currentTransaction, getTransactionsHistory());
    }
}
