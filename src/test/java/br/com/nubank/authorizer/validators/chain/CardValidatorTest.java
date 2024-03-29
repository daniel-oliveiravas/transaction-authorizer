package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
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

        List<String> violations = new ArrayList<>();
        cardValidator.handle(account, currentTransaction, Collections.emptyList(), violations);

        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void shouldNotHaveViolationWithActiveCard() {
        Account account = TestsHelper.createAccount(100, true);
        Transaction currentTransaction = TestsHelper.createTransaction("any", LocalDateTime.now(), 100);

        List<String> violations = Collections.emptyList();
        cardValidator.handle(account, currentTransaction, Collections.emptyList(), violations);

        Assert.assertEquals(0, violations.size());
    }
}