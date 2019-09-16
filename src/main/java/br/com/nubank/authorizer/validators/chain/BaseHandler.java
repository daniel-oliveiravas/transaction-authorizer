package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public abstract class BaseHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (nextHandler != null) {
            nextHandler.handle(account, transaction, history, violations);
        }
    }
}
