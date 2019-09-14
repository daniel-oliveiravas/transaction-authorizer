package br.com.nubank.core;

import br.com.nubank.authorizer.BasicAuthorizer;
import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.authorizer.validators.chain.BasicValidatorsChain;
import br.com.nubank.core.interfaces.OperationHandler;
import br.com.nubank.models.Account;
import br.com.nubank.models.Operation;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionResult;

public class TransactionHandler implements OperationHandler {

    private Authorizer authorizer;
    private AccountHolder accountHolder;

    TransactionHandler(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
        this.authorizer = new BasicAuthorizer(new BasicValidatorsChain());
    }

    @Override
    public TransactionResult handleOperation(Operation operation) {
        Transaction transaction = (Transaction) operation;
        Account account = this.accountHolder.getAccount();
        TransactionResult transactionResult = authorizer.authorizeTransaction(account, transaction);
        if(transactionResult.getViolations().isEmpty()) {
            subtractAmountFromAccountLimit(account, transaction);
        }
        return transactionResult;
    }

    private void subtractAmountFromAccountLimit(Account account, Transaction transaction) {
        account.setAvailableLimit(account.getAvailableLimit() - transaction.getAmount());
    }
}
