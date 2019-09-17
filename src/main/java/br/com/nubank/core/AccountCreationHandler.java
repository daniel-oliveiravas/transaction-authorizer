package br.com.nubank.core;

import br.com.nubank.core.interfaces.OperationHandler;
import br.com.nubank.models.Account;
import br.com.nubank.models.Operation;
import br.com.nubank.models.TransactionResult;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static br.com.nubank.enums.Violations.ACCOUNT_ALREADY_INITIALIZED;

public class AccountCreationHandler implements OperationHandler {

    private AccountHolder accountHolder;

    AccountCreationHandler(AccountHolder accountHolder) {
        this.accountHolder = accountHolder;
    }

    public TransactionResult handleOperation(Operation operation) {
        Account account = (Account) operation;
        List<String> violations;
        if (Objects.isNull(this.accountHolder.getAccount())) {
            accountHolder.setAccount(account);
            violations = Collections.emptyList();
        } else {
            violations = Collections.singletonList(ACCOUNT_ALREADY_INITIALIZED.getCode());
        }

        return new TransactionResult(this.accountHolder.getAccount(), violations);
    }
}
