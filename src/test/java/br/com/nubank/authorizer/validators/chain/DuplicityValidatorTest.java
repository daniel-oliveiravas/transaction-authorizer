package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.utils.TestsHelper;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DuplicityValidatorTest {

    private DuplicityValidator duplicityValidator;

    @Before
    public void setup() {
        duplicityValidator = new DuplicityValidator();
    }

    @Test
    public void duplicatedTransactionWithinTwoMinutesDifferenceShouldReturnViolation() {
        List<String> violations = new ArrayList<>();
        String merchant = "Don Pollo";
        Integer amount = 280;

        Transaction currentTransaction = TestsHelper.createTransaction(merchant, LocalDateTime.now(), amount);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory(merchant, 1, amount);
        Account simpleAccount = TestsHelper.createAccount(0, true);

        duplicityValidator.handle(simpleAccount, currentTransaction, transactionsHistory, violations);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void duplicatedTransactionWithMoreThanTwoMinutesDifferenceShouldBeValid() {
        List<String> violations = new ArrayList<>();
        String merchant = "Don Pollo";
        Integer amount = 280;

        LocalDateTime now = LocalDateTime.now();

        Transaction currentTransaction = TestsHelper.createTransaction(merchant, now, amount);
        List<Transaction> transactionsHistory = createValidTransactionsHistory(merchant, amount, now);
        Account simpleAccount = TestsHelper.createAccount(0, true);

        duplicityValidator.handle(simpleAccount, currentTransaction, transactionsHistory, violations);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void sameMerchantButDifferentAmountsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        String merchant = "Don Pollo";
        Integer amount = 280;
        Integer secondAmount = 35;
        int differenceInMinutes = 3;

        Transaction transactionWithDifferentAmount = TestsHelper.createTransaction(merchant, LocalDateTime.now().plusMinutes(differenceInMinutes), secondAmount);
        List<Transaction> transactionsHistory = TestsHelper.createTransactionHistory(merchant, 1, amount);
        Account simpleAccount = TestsHelper.createAccount(0, true);

        duplicityValidator.handle(simpleAccount, transactionWithDifferentAmount, transactionsHistory, violations);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void differentMerchantSameAmountsShouldBeValid() {
        List<String> violations = new ArrayList<>();
        String merchant = "Don Pollo";
        String secondMerchant = "La Federal";
        Integer amount = 280;

        LocalDateTime now = LocalDateTime.now();

        Transaction transactionWithDifferentAmount = TestsHelper.createTransaction(merchant, now, amount);
        List<Transaction> transactionsHistory = createTransactionsFromDifferentMerchant(merchant, secondMerchant, amount);
        Account simpleAccount = TestsHelper.createAccount(0, true);

        duplicityValidator.handle(simpleAccount, transactionWithDifferentAmount, transactionsHistory, violations);
        Assert.assertEquals(0, violations.size());
    }

    private List<Transaction> createTransactionsFromDifferentMerchant(String firstMerchant, String secondMerchant, Integer amount) {
        return Arrays.asList(
                TestsHelper.createTransaction(firstMerchant, LocalDateTime.now().minusMinutes(3), amount),
                TestsHelper.createTransaction(secondMerchant, LocalDateTime.now().minusMinutes(1), amount)
        );
    }

    private List<Transaction> createValidTransactionsHistory(String merchant, Integer amount, LocalDateTime localDateTime) {
        LocalDateTime fiveMinutesAgo = LocalDateTime.from(localDateTime).minusMinutes(5);
        return Arrays.asList(
                TestsHelper.createTransaction(merchant, fiveMinutesAgo, amount),
                TestsHelper.createTransaction(merchant, fiveMinutesAgo.minusMinutes(5), amount)
        );
    }
}