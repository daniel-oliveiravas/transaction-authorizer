package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.List;

interface Handler {

    void setNext(Handler handler);

    void handle(Operation operation, List<String> violations);
}
