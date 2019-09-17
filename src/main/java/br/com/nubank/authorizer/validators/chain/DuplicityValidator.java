package br.com.nubank.authorizer.validators.chain;

import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.nubank.enums.Violations.DUPLICATED_TRANSACTION;

public class DuplicityValidator extends BaseValidator {

    private static final long FREQUENCY_MINUTES_RANGE = 2;
    private static final int MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS = 0;

    @Override
    public void handle(Account account, Transaction transaction, List<Transaction> history, List<String> violations) {
        String currentMerchantPurchase = transaction.getMerchant();
        List<Transaction> merchantTransactions = groupTransactionByMerchant(history).get(currentMerchantPurchase);

        if (merchantTransactions != null && merchantTransactions.size() > 0) {
            long doubledTransactionsCount = getDoubledTransactionsFromLastMinutesCount(merchantTransactions,
                    transaction.getAmount(), transaction.getTime());

            if (doubledTransactionsCount > MAXIMUM_ALLOWED_DOUBLED_TRANSACTIONS) {
                violations.add(DUPLICATED_TRANSACTION.getCode());
            }
        }

        super.handle(account, transaction, history, violations);
    }

    private long getDoubledTransactionsFromLastMinutesCount(List<Transaction> transactionsHistory,
                                                            Integer currentAmount,
                                                            LocalDateTime currentTransactionTime) {
        return transactionsHistory.stream()
                .filter(transaction -> transaction.getAmount().equals(currentAmount) &&
                        hasHappenedInLastTwoMinutes(currentTransactionTime, transaction.getTime()))
                .count();
    }

    private Map<String, List<Transaction>> groupTransactionByMerchant(List<Transaction> transactionsHistory) {
        return transactionsHistory.stream().collect(Collectors.groupingBy(Transaction::getMerchant));
    }

    private boolean hasHappenedInLastTwoMinutes(LocalDateTime currentTransactionTime,
                                                LocalDateTime pastTransactionTime) {
        LocalDateTime twoMinutesAgo = LocalDateTime.from(currentTransactionTime).minusMinutes(FREQUENCY_MINUTES_RANGE);
        return pastTransactionTime.isAfter(twoMinutesAgo);
    }
}
