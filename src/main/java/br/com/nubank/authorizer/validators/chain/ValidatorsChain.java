package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public interface ValidatorsChain {

    List<String> validate(Account account, Transaction transaction, List<Transaction> history);
}
