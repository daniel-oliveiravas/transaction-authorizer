package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Account;
import br.com.nubank.authorizer.models.Operation;
import br.com.nubank.authorizer.models.Transaction;
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
        frequencyValidator.handle(createOperation(3), violations);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void threeCloseTransactionsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        frequencyValidator.handle(createOperation(2), violations);
        Assert.assertEquals(0, violations.size());
    }

    private Operation createOperation(int transactionsHistorySize) {
        return new Operation(createAccount(), createTransaction(LocalDateTime.now()), createInvalidTransactionHistory(transactionsHistorySize));
    }

    private Account createAccount() {
        return new Account(0, true);
    }

    private List<Transaction> createInvalidTransactionHistory(int transactionsQuantity) {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1);
        List<Transaction> transactionsHistory = new ArrayList<>();
        for (int i = 0; i < transactionsQuantity; i++) {
            transactionsHistory.add(createTransaction(dateTime.plusSeconds(i + 1)));
        }

        return transactionsHistory;
    }

    private Transaction createTransaction(LocalDateTime time) {
        return new Transaction("any", 0, LocalDateTime.from(time));
    }

}