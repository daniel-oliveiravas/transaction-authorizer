package br.com.nubank.core;

import br.com.nubank.authorizer.BasicAuthorizer;
import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.core.exceptions.AccountNotInitializedException;
import br.com.nubank.core.interfaces.OperationHandler;
import br.com.nubank.models.Account;
import br.com.nubank.models.Operation;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;

import java.util.Objects;

public class TransactionHandler implements OperationHandler {

    private Authorizer authorizer;
    private AccountHolder accountHolder;

    TransactionHandler(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
        this.authorizer = new BasicAuthorizer();
    }

    @Override
    public TransactionResult handleOperation(Operation operation) {
        Transaction transaction = (Transaction) operation;
        Account account = this.accountHolder.getAccount();
        if (Objects.isNull(account)) {
            throw new AccountNotInitializedException();
        }
        TransactionResult transactionResult = authorizer.authorizeTransaction(account, transaction);
        if(transactionResult.getViolations().isEmpty()) {
            account.addTransaction(transaction);
            subtractAmountFromAccountLimit(account, transaction);
        }
        return transactionResult;
    }

    private void subtractAmountFromAccountLimit(Account account, Transaction transaction) {
        account.setAvailableLimit(account.getAvailableLimit() - transaction.getAmount());
    }
}
