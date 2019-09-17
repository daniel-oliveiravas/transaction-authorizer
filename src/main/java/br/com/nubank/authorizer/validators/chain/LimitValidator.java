package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

import static br.com.nubank.enums.Violations.INSUFFICIENT_LIMIT;

public class LimitValidator extends BaseValidator {

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        if (account.getAvailableLimit() < transaction.getAmount()) {
            violations.add(INSUFFICIENT_LIMIT.getCode());
        }
        super.handle(account, transaction, history, violations);
    }
}
