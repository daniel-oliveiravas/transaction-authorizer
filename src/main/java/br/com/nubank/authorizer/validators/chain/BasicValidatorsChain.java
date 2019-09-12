package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.ArrayList;
import java.util.List;

public class BasicValidatorsChain implements ValidatorsChain {

    private Handler initialHandler;

    public BasicValidatorsChain() {
        initialHandler = new CardValidator();
        initialHandler.setNext(new LimitValidator());
    }

    @Override
    public List<String> validate(Operation operation) {
        List<String> violations = new ArrayList<>();
        initialHandler.handle(operation, violations);
        return violations;
    }
}
