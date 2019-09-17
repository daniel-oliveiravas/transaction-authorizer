package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public abstract class BaseValidator implements Validator {

    private Validator nextValidator;

    @Override
    public void setNext(Validator validator) {
        this.nextValidator = validator;
    }

    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (nextValidator != null) {
            nextValidator.handle(account, transaction, history, violations);
        }
    }
}
