package br.com.nubank.authorizer.interfaces;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;

import java.util.List;

public interface Authorizer {

    TransactionResult authorizeTransaction(Account account, Transaction transaction, List<Transaction> history);
}
