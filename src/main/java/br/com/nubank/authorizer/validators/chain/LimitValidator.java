package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public class LimitValidator extends BaseValidator {

    private static final String INSUFFICIENT_LIMIT_VIOLATION = "insufficient-limit";

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (account.getAvailableLimit() < transaction.getAmount()) {
            violations.add(INSUFFICIENT_LIMIT_VIOLATION);
        }
        super.handle(account, transaction, history, violations);
    }
}
