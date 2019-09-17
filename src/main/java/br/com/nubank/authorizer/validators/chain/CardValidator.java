package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public class CardValidator extends BaseValidator {

    private static final String CARD_VIOLATION = "card-not-active";

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (account.getActiveCard().equals(Boolean.FALSE)) {
            violations.add(CARD_VIOLATION);
        }

        super.handle(account, transaction, history, violations);
    }
}
