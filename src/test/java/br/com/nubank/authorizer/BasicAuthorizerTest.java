package br.com.nubank.authorizer;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import br.com.nubank.models.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthorizerTest {

    @InjectMocks
    private BasicAuthorizer basicAuthorizer;

    @Mock
    private ValidatorsChain validatorsChain;

    @Test
    public void authorizeTransactionSuccessfully() {
        when(validatorsChain.validate(any())).thenReturn(Collections.emptyList());
        Account account = TestsHelper.createAccount(100, false);

        basicAuthorizer.authorizeTransaction(account, TestsHelper.createTransaction("any", LocalDateTime.now(), 100));

        verify(validatorsChain, times(1)).validate(any());
    }
}