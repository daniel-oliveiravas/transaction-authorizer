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

public class FrequencyValidatorTest {

    private FrequencyValidator frequencyValidator;

    @Before
    public void setup() {
        frequencyValidator = new FrequencyValidator();
    }

    @Test
    public void fourCloseTransactionsShouldHaveViolations() {
        List<String> violations = new ArrayList<>();
        frequencyValidator.handle(createTransactionAuthorization(3), violations);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void threeCloseTransactionsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        frequencyValidator.handle(createTransactionAuthorization(2), violations);
        Assert.assertEquals(0, violations.size());
    }

    private TransactionAuthorization createTransactionAuthorization(int transactionsHistorySize) {
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", transactionsHistorySize, 200);
        Account simpleAccount = TestsHelper.createAccount(0, true, transactionsHistory);
        return TestsHelper.createTransactionAuthorization(simpleAccount, currentTransaction);
    }
}