package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Operation;
import br.com.nubank.authorizer.models.Transaction;
import br.com.nubank.authorizer.utils.TestsHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardValidatorTest {

    private CardValidator cardValidator;

    @Before
    public void setup() {
        cardValidator = new CardValidator();
    }

    @Test
    public void shouldHaveViolationWithInactiveCard() {
        Account account = TestsHelper.createAccount(100, false);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", 1, 100);
        Operation operation = TestsHelper.createOperation(account, currentTransaction, transactionsHistory);

        List<String> violations = new ArrayList<>();
        cardValidator.handle(operation, violations);

        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotHaveViolationWithActiveCard() {
        Account account = TestsHelper.createAccount(100, true);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory("any", 1, 100);
        Operation operation = TestsHelper.createOperation(account, currentTransaction, transactionsHistory);

        List<String> violations = Collections.emptyList();
        cardValidator.handle(operation, violations);

        Assert.assertEquals(0, violations.size());
    }
}