package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;

import java.util.List;

public class CardValidator extends BaseHandler {

    private static final String CARD_VIOLATION = "card-not-active";

    @Override
    public void handle(Operation operation, List<String> violations) {
        if (operation.getAccount().getActiveCard().equals(Boolean.FALSE)) {
            violations.add(CARD_VIOLATION);
        }

        super.handle(operation, violations);
    }
}
