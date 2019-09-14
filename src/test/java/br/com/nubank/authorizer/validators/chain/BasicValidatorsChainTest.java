package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.models.TransactionAuthorization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BasicValidatorsChainTest {

    @InjectMocks
    private BasicValidatorsChain basicValidatorsChain;

    @Mock
    private CardValidator cardValidator;

    @Test
    public void validateViolationsSuccessfully() {
        TransactionAuthorization transactionAuthorization = TestsHelper.createTransactionAuthorization(
                TestsHelper.createAccount(1, false),
                TestsHelper.createTransaction("any", LocalDateTime.now(), 100)
        );
        basicValidatorsChain.validate(transactionAuthorization);
        verify(cardValidator, times(1)).handle(any(), any());
    }
}