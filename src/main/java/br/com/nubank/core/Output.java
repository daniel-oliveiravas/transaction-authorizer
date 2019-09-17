package br.com.nubank.core;

import br.com.nubank.models.TransactionResult;

public interface Output {

    void send(TransactionResult transactionResult);
}
