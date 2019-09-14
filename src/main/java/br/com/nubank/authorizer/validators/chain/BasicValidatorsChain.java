package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.TransactionAuthorization;

import java.util.ArrayList;
import java.util.List;

public class BasicValidatorsChain implements ValidatorsChain {

    private Handler initialHandler;

    public BasicValidatorsChain() {
        initialHandler = new CardValidator();
        LimitValidator limitValidator = new LimitValidator();
        DuplicityValidator duplicityValidator = new DuplicityValidator();
        FrequencyValidator frequencyValidator = new FrequencyValidator();

        initialHandler.setNext(limitValidator);
        limitValidator.setNext(duplicityValidator);
        duplicityValidator.setNext(frequencyValidator);
    }

    @Override
    public List<String> validate(TransactionAuthorization transactionAuthorization) {
        List<String> violations = new ArrayList<>();
        initialHandler.handle(transactionAuthorization, violations);
        return violations;
    }
}
