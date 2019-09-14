package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.TransactionAuthorization;

import java.util.List;

public interface ValidatorsChain {

    List<String> validate(TransactionAuthorization transactionAuthorization);
}
