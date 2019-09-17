package br.com.nubank.core;

import br.com.nubank.authorizer.BasicAuthorizer;
import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.core.exceptions.AccountNotInitializedException;
import br.com.nubank.core.interfaces.OperationHandler;
import br.com.nubank.models.Account;
import br.com.nubank.models.Operation;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionHandler implements OperationHandler {

    private Authorizer authorizer;
    private AccountHolder accountHolder;
    private List<Transaction> transactionHistory;

    TransactionHandler(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
        this.authorizer = new BasicAuthorizer();
        this.transactionHistory = new ArrayList<>();
    }

    @Override
    public TransactionResult handleOperation(Operation operation) {
        Transaction transaction = (Transaction) operation;
        Account account = this.accountHolder.getAccount();
        if (Objects.isNull(account)) {
            throw new AccountNotInitializedException();
        }
        List<String> violations = authorizer.authorizeTransaction(account, transaction, transactionHistory);
        if (Objects.nonNull(violations) && violations.isEmpty()) {
            transactionHistory.add(transaction);
            accountHolder.setNewAvailableLimit(calculateNewAvailableLimit(account, transaction));
        }
        return new TransactionResult(accountHolder.getAccount(), violations);
    }

    private int calculateNewAvailableLimit(Account account, Transaction transaction) {
        return account.getAvailableLimit() - transaction.getAmount();
    }
}
