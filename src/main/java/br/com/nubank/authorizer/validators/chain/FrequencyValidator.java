package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;
import br.com.nubank.authorizer.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class FrequencyValidator extends BaseHandler {

    private static final String HIGH_FREQUENCY_VIOLATION = "high-frequency-small-interval";

    @Override
    public void handle(Operation operation, List<String> violations) {
        List<Transaction> transactionsHistory = operation.getTransactionsHistory();

        LocalDateTime start = LocalDateTime.now().minusMinutes(2);
        LocalDateTime end = LocalDateTime.now();

        long transactionsInLastTwoMinutesFromHistory = transactionsHistory.stream()
                .filter(transaction -> isInvalidRange(start, end, transaction.getTime()))
                .count();

        LocalDateTime currentTransactionTime = operation.getCurrentTransaction().getTime();
        int currentTransaction = isInvalidRange(start, end, currentTransactionTime) ? 1 : 0;

        if (transactionsInLastTwoMinutesFromHistory + currentTransaction > 3) {
            violations.add(HIGH_FREQUENCY_VIOLATION);
        }

        super.handle(operation, violations);
    }

    private boolean isInvalidRange(LocalDateTime start, LocalDateTime end, LocalDateTime time) {
        return time.isAfter(start) && time.isBefore(end);
    }
}
