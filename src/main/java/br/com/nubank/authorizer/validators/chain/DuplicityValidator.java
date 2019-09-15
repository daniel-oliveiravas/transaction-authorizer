package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionAuthorization;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DuplicityValidator extends BaseHandler {

    private static final String DUPLICITY_VIOLATION = "doubled-transaction";
    private static final long FREQUENCY_MINUTES_RANGE = 2;
    private static final int MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS = 0;

    @Override
    public void handle(TransactionAuthorization transactionAuthorization, List<String> violations) {
        Transaction currentTransaction = transactionAuthorization.getTransaction();
        String currentMerchantPurchase = currentTransaction.getMerchant();
        List<Transaction> merchantTransactions = groupTransactionByMerchant(transactionAuthorization.getAccount().getHistory()).get(currentMerchantPurchase);

        if (merchantTransactions != null && merchantTransactions.size() > 0) {
            long doubledTransactionsCount = getDoubledTransactionsFromLastMinutesCount(merchantTransactions, currentTransaction.getAmount(), currentTransaction.getTime());

            if (doubledTransactionsCount > MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS) {
                violations.add(DUPLICITY_VIOLATION);
            }
        }

        super.handle(transactionAuthorization, violations);
    }

    private long getDoubledTransactionsFromLastMinutesCount(List<Transaction> transactionsHistory, Integer currentAmount, LocalDateTime currentTransactionTime) {
        return transactionsHistory.stream()
                .filter(transaction -> transaction.getAmount().equals(currentAmount) && hasHappenedInLastTwoMinutes(currentTransactionTime, transaction.getTime()))
                .count();
    }

    private Map<String, List<Transaction>> groupTransactionByMerchant(List<Transaction> transactionsHistory) {
        return transactionsHistory.stream().collect(Collectors.groupingBy(Transaction::getMerchant));
    }

    private boolean hasHappenedInLastTwoMinutes(LocalDateTime currentTransactionTime, LocalDateTime pastTransactionTime) {
        LocalDateTime twoMinutesAgo = LocalDateTime.from(currentTransactionTime).minusMinutes(FREQUENCY_MINUTES_RANGE);
        return pastTransactionTime.isAfter(twoMinutesAgo);
    }
}
