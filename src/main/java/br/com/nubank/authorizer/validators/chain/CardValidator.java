package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.TransactionAuthorization;

import java.util.List;

public class CardValidator extends BaseHandler {

    private static final String CARD_VIOLATION = "card-not-active";

    @Override
    public void handle(TransactionAuthorization transactionAuthorization, List<String> violations) {
        if (transactionAuthorization.getAccount().getActiveCard().equals(Boolean.FALSE)) {
            violations.add(CARD_VIOLATION);
        }

        super.handle(transactionAuthorization, violations);
    }
}
