package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BasicValidatorsChain implements ValidatorsChain {

    private Validator initialValidator;

    public BasicValidatorsChain() {
        initialValidator = new CardValidator();
        LimitValidator limitValidator = new LimitValidator();
        DuplicityValidator duplicityValidator = new DuplicityValidator();
        FrequencyValidator frequencyValidator = new FrequencyValidator();

        initialValidator.setNext(limitValidator);
        limitValidator.setNext(duplicityValidator);
        duplicityValidator.setNext(frequencyValidator);
    }

    @Override
    public List<String> validate(Account account, Transaction transaction, List<Transaction> history) {
        List<String> violations = new ArrayList<>();
        initialValidator.handle(account, transaction, history, violations);
        return violations;
    }
}
