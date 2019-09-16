package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class FrequencyValidator extends BaseHandler {

    private static final String HIGH_FREQUENCY_VIOLATION = "high-frequency-small-interval";
    private static final int FREQUENCY_MINUTES_RANGE = 2;
    private static final int MAXIMUM_TRANSACTIONS_ALLOWED = 3;
    private static final int CURRENT_TRANSACTION_COUNT = 1;

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        LocalDateTime start = LocalDateTime.from(transaction.getTime()).minusMinutes(FREQUENCY_MINUTES_RANGE);
        LocalDateTime end = LocalDateTime.from(transaction.getTime());

        long transactionsInLastTwoMinutesFromHistory = getLastTwoMinutesTransactionsCount(history, start, end);

        if (transactionsInLastTwoMinutesFromHistory + CURRENT_TRANSACTION_COUNT > MAXIMUM_TRANSACTIONS_ALLOWED) {
            violations.add(HIGH_FREQUENCY_VIOLATION);
        }

        super.handle(account, transaction, history, violations);
    }

    private long getLastTwoMinutesTransactionsCount(List<Transaction> transactionsHistory, LocalDateTime start,
                                                    LocalDateTime end) {
        return transactionsHistory.stream()
                .filter(transaction -> isInvalidRange(start, end, transaction.getTime()))
                .count();
    }

    private boolean isInvalidRange(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        return time.isAfter(start) && time.isBefore(end);
    }
}
