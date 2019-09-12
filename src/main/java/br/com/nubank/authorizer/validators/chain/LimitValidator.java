package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.List;

public class LimitValidator extends BaseHandler {

    private static final String INSUFFICIENT_LIMIT_VIOLATION = "insufficient-limit";

    @Override
    public void handle(Operation operation, List<String> violations) {
        if (operation.getAccount().getAvailableLimit() < operation.getCurrentTransaction().getAmount()) {
            violations.add(INSUFFICIENT_LIMIT_VIOLATION);
        }
        super.handle(operation, violations);
    }
}
