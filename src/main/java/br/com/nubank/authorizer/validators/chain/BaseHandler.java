package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.List;

public abstract class BaseHandler implements Handler {

    private Handler nextHandler;

    @Override
    public void setNext(Handler handler) {
        this.nextHandler = handler;
    }

    public void handle(Operation operation, List<String> violations) {
        if (nextHandler != null) {
            nextHandler.handle(operation, violations);
        }
    }
}
