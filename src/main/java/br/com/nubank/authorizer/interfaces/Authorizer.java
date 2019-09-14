package br.com.nubank.authorizer.interfaces;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;

public interface Authorizer {

    TransactionResult authorizeTransaction(Account account, Transaction transaction);
}
