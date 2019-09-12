package br.com.nubank.authorizer;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class BasicAuthorizerTest {

    @Test
    public void shouldAddTransactionObjectToListWhenAuthorizing() {
        BasicAuthorizer basicAuthorizer = new BasicAuthorizer();
        basicAuthorizer.authorize(createAccountWithActiveCard(100), createSimpleTransaction());

        Assert.assertEquals(basicAuthorizer.getTransactionsHistory().size(), 1);
    }

    private Account createAccountWithActiveCard(Integer availableLimit) {
        return new Account(availableLimit, true);
    }

    private Transaction createSimpleTransaction() {
        return new Transaction("Any merchant", 100, LocalDateTime.now());
    }
}