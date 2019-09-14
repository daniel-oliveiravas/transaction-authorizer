package br.com.nubank.authorizer;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthorizerTest {

    @InjectMocks
    private BasicAuthorizer basicAuthorizer;

    @Mock
    private ValidatorsChain validatorsChain;

    @Test
    public void shouldAddTransactionToHistoryAfterAuthorizeWithoutViolations() {
        when(validatorsChain.validate(any())).thenReturn(Collections.emptyList());
        TransactionResult transactionResult = basicAuthorizer.authorizeTransaction(createAccountWithActiveCard(), createSimpleTransaction());

        Mockito.verify(validatorsChain, Mockito.times(1)).validate(any());
        Assert.assertEquals(basicAuthorizer.getTransactionsHistory().size(), 1);
        Assert.assertEquals(0, transactionResult.getViolations().size());
    }

    @Test
    public void shouldNotAddTransactionToHistoryAfterAuthorizeWithViolations() {
        String violationName = "card-not-active";
        when(validatorsChain.validate(any())).thenReturn(Collections.singletonList(violationName));
        TransactionResult transactionResult = basicAuthorizer.authorizeTransaction(createAccountWithActiveCard(), createSimpleTransaction());

        Mockito.verify(validatorsChain, Mockito.times(1)).validate(any());
        Assert.assertEquals(basicAuthorizer.getTransactionsHistory().size(), 0);
        Assert.assertEquals(1, transactionResult.getViolations().size());
        Assert.assertEquals("card-not-active", transactionResult.getViolations().get(0));
    }

    private Account createAccountWithActiveCard() {
        return new Account(100, true);
    }

    private Transaction createSimpleTransaction() {
        return new Transaction("Any merchant", 100, LocalDateTime.now());
    }
}