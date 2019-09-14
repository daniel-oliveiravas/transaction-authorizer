package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.TransactionAuthorization;

import java.util.List;

public class LimitValidator extends BaseHandler {

    private static final String INSUFFICIENT_LIMIT_VIOLATION = "insufficient-limit";

    @Override
    public void handle(TransactionAuthorization transactionAuthorization, List<String> violations) {
        if (transactionAuthorization.getAccount().getAvailableLimit() < transactionAuthorization.getTransaction().getAmount()) {
            violations.add(INSUFFICIENT_LIMIT_VIOLATION);
        }
        super.handle(transactionAuthorization, violations);
    }
}
