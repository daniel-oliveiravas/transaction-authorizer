package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.List;

public interface ValidatorsChain {

    List<String> validate(Operation operation);
}
