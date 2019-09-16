package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
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
        Account simpleAccount = TestsHelper.createAccount(0, true);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", 3, 200);

        frequencyValidator.handle(simpleAccount, currentTransaction, transactionsHistory, violations);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void threeCloseTransactionsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        Account simpleAccount = TestsHelper.createAccount(0, true);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", 2, 200);

        frequencyValidator.handle(simpleAccount, currentTransaction, transactionsHistory, violations);
        Assert.assertEquals(0, violations.size());
    }
}