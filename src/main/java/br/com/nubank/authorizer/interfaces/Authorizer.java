package br.com.nubank.authorizer.interfaces;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Transaction;
import br.com.nubank.authorizer.models.TransactionResult;

public interface Autorizer {

    TransactionResult authorize(Account account, Transaction transaction);
}