package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

interface Handler {

    void setNext(Handler handler);

    void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations);
}
