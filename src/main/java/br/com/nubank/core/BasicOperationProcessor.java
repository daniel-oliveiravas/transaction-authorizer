package br.com.nubank.core;

import br.com.nubank.core.exceptions.InvalidOperationTypeException;
import br.com.nubank.core.interfaces.OperationHandler;
import br.com.nubank.core.interfaces.OperationProcessor;
import br.com.nubank.models.Operation;
import br.com.nubank.models.TransactionResult;
import br.com.nubank.output.StdoutOutput;

public class BasicOperationProcessor implements OperationProcessor {

    private Output output;
    private AccountCreationHandler accountCreationHandler;
    private OperationHandler transactionHandler;

    public BasicOperationProcessor() {
        this.output = new StdoutOutput();
        AccountHolder accountHolder = new AccountHolder();
        this.accountCreationHandler = new AccountCreationHandler(accountHolder);
        this.transactionHandler = new TransactionHandler(accountHolder);
    }

    @Override
    public void process(Operation operation) throws InvalidOperationTypeException {
        TransactionResult transactionResult = processOperationByType(operation);
        output.send(transactionResult);
    }

    private TransactionResult processOperationByType(Operation operation) throws InvalidOperationTypeException {
        switch (operation.getOperationType()) {
            case ACCOUNT_CREATION:
                return this.accountCreationHandler.handleOperation(operation);
            case TRANSACTION:
                return this.transactionHandler.handleOperation(operation);
            default:
                throw new InvalidOperationTypeException();
        }
    }
}
