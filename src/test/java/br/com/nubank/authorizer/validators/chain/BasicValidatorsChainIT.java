package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static br.com.nubank.enums.Violations.INACTIVE_CARD;
import static br.com.nubank.enums.Violations.INSUFFICIENT_LIMIT;

public class BasicValidatorsChainIT {

    @Test
    public void shouldValidateOperationSuccessfully() {
        ValidatorsChain validatorsChain = new BasicValidatorsChain();

        List<String> violations = validatorsChain.validate(createAccountWithInactiveCard(100), createSimpleTransaction(200), Collections.emptyList());

        Assert.assertTrue(violations.contains(INACTIVE_CARD.getCode()));
        Assert.assertTrue(violations.contains(INSUFFICIENT_LIMIT.getCode()));
    }

    private Account createAccountWithInactiveCard(Integer availableLimit) {
        return new Account(availableLimit, false);
    }

    private Transaction createSimpleTransaction(Integer amount) {
        return new Transaction("Any merchant", amount, LocalDateTime.now());
    }
}
