package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.authorizer.models.Operation;
import br.com.nubank.authorizer.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicityValidator extends BaseHandler {

    private static final String DUPLICITY_VIOLATION = "doubled-transaction";
    private static final long FREQUENCY_MINUTES_RANGE = 2;
    private static final int MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS = 0;

    @Override
    public void handle(Operation operation, List<String> violations) {
        Transaction currentTransaction = operation.getCurrentTransaction();
        String currentMerchantPurchase = currentTransaction.getMerchant();
        List<Transaction> merchantTransactions = groupTransactionByMerchant(operation.getTransactionsHistory()).get(currentMerchantPurchase);

        if (merchantTransactions != null && merchantTransactions.size() > 0) {
            long doubledTransactionsCount = getDoubledTransactionsFromLastMinutesCount(merchantTransactions, currentTransaction.getAmount());

            if (doubledTransactionsCount > MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS) {
                violations.add(DUPLICITY_VIOLATION);
            }
        }

        super.handle(operation, violations);
    }

    private long getDoubledTransactionsFromLastMinutesCount(List<Transaction> transactionsHistory, Integer currentAmount) {
        return transactionsHistory.stream()
                .filter(transaction -> transaction.getAmount().equals(currentAmount) && hasHappenedInLastTwoMinutes(transaction.getTime()))
                .count();
    }

    private Map<String, List<Transaction>> groupTransactionByMerchant(List<Transaction> transactionsHistory) {
        return transactionsHistory.stream().collect(Collectors.groupingBy(Transaction::getMerchant));
    }

    private boolean hasHappenedInLastTwoMinutes(LocalDateTime time) {
        LocalDateTime twoMinutesAgo = LocalDateTime.now().minusMinutes(FREQUENCY_MINUTES_RANGE);
        return time.isAfter(twoMinutesAgo);
    }
}
