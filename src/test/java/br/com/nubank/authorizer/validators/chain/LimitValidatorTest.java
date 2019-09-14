package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionAuthorization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LimitValidatorTest {

    private LimitValidator limitValidator;

    @Before
    public void setup() {
        limitValidator = new LimitValidator();
    }

    @Test
    public void shouldHaveViolationWhenLimitIsInsufficient() {
        Integer availableLimit = 50;
        Integer operationAmount = 1000;
        List<String> violations = new ArrayList<>();

        limitValidator.handle(createOperation(availableLimit, operationAmount), violations);

        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotHaveViolationWhenLimitIsEnough() {
        Integer availableLimit = 100;
        Integer operationAmount = 50;
        List<String> violations = new ArrayList<>();

        limitValidator.handle(createOperation(availableLimit, operationAmount), violations);

        Assert.assertEquals(0, violations.size());
    }

    private TransactionAuthorization createOperation(Integer availableLimit, Integer operationAmount) {
        Account account = TestsHelper.createAccount(availableLimit, true);
        Transaction transaction = TestsHelper.createTransaction("any", LocalDateTime.now(), operationAmount);
        return TestsHelper.createTransactionAuthorization(account, transaction);
    }
}