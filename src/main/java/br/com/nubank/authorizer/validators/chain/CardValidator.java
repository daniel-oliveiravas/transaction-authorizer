package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

import static br.com.nubank.enums.Violations.INACTIVE_CARD;

public class CardValidator extends BaseValidator {

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (account.getActiveCard().equals(Boolean.FALSE)) {
            violations.add(INACTIVE_CARD.getCode());
        }

        super.handle(account, transaction, history, violations);
    }
}
