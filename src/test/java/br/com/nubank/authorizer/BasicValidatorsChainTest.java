package br.com.nubank.authorizer;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Operation;
import br.com.nubank.authorizer.models.Transaction;
import br.com.nubank.authorizer.validators.chain.BasicValidatorsChain;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class BasicValidatorsChainTest {

    @Test
    public void shouldValidateOperationSuccessfully() {
        ValidatorsChain validatorsChain = new BasicValidatorsChain();

        List<String> violations = validatorsChain.validate(createOperationWithMultipleViolations());

        Assert.assertTrue(violations.contains("card-not-active"));
        Assert.assertTrue(violations.contains("insufficient-limit"));
    }

    private Operation createOperationWithMultipleViolations() {
        return new Operation(createAccountWithInactiveCard(100),
                createSimpleTransaction(200),
                Collections.emptyList());
    }

    private Account createAccountWithInactiveCard(Integer availableLimit) {
        return new Account(availableLimit, false);
    }

    private Transaction createSimpleTransaction(Integer amount) {
        return new Transaction("Any merchant", amount, LocalDateTime.now());
    }
}
