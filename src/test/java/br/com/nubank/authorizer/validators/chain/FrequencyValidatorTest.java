package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Transaction;
import br.com.nubank.authorizer.models.TransactionAuthorization;
import br.com.nubank.authorizer.utils.TestsHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FrequencyValidatorTest {

    private FrequencyValidator frequencyValidator;

    @Before
    public void setup() {
        frequencyValidator = new FrequencyValidator();
    }

    @Test
    public void fourCloseTransactionsShouldHaveViolations() {
        List<String> violations = new ArrayList<>();
        frequencyValidator.handle(createOperations(3), violations);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void threeCloseTransactionsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        frequencyValidator.handle(createOperations(2), violations);
        Assert.assertEquals(0, violations.size());
    }

    private TransactionAuthorization createOperations(int transactionsHistorySize) {
        Account simpleAccount = TestsHelper.createAccount(0, true);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", transactionsHistorySize, 200);
        return TestsHelper.createOperation(simpleAccount, currentTransaction, transactionsHistory);
    }
}