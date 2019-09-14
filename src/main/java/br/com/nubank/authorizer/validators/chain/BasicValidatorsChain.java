package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.TransactionAuthorization;

import java.util.ArrayList;
import java.util.List;

public class BasicValidatorsChain implements ValidatorsChain {

    private Handler initialHandler;

    public BasicValidatorsChain() {
        initialHandler = new CardValidator();
        initialHandler.setNext(new LimitValidator());
    }

    @Override
    public List<String> validate(TransactionAuthorization transactionAuthorization) {
        List<String> violations = new ArrayList<>();
        initialHandler.handle(transactionAuthorization, violations);
        return violations;
    }
}
