package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.TransactionAuthorization;

import java.util.List;

interface Handler {

    void setNext(Handler handler);

    void handle(TransactionAuthorization transactionAuthorization, List<String> violations);
}
