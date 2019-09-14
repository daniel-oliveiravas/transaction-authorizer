package br.com.nubank.output;

import br.com.nubank.models.TransactionResult;

public interface Output {

    void send(TransactionResult transactionResult);
}
