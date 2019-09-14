package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.TransactionAuthorization;

import java.util.List;

public abstract class BaseHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    public void handle(TransactionAuthorization transactionAuthorization, List<String> violations) {
        if (nextHandler != null) {
            nextHandler.handle(transactionAuthorization, violations);
        }
    }
}
