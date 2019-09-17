package br.com.nubank.authorizer.interfaces;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public interface Authorizer {

    List<String> authorizeTransaction(Account account, Transaction transaction, List<Transaction> history);
}
