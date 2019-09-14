package br.com.nubank.core.interfaces;

import br.com.nubank.models.Operation;
import br.com.nubank.models.TransactionResult;

public interface OperationHandler {

    TransactionResult handleOperation(Operation operation);
}
