package br.com.nubank.authorizer;

import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.authorizer.validators.chain.BasicValidatorsChain;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;
import br.com.nubank.models.TransactionAuthorization;
import br.com.nubank.models.TransactionResult;

import java.util.List;

public class BasicAuthorizer implements Authorizer {

    private ValidatorsChain validatorsChain;

    public BasicAuthorizer() {
        this.validatorsChain = new BasicValidatorsChain();
    }

    @Override
    public TransactionResult authorizeTransaction(Account account, Transaction transaction) {
        TransactionAuthorization transactionAuthorization = new TransactionAuthorization(account, transaction);
        List<String> violations = validatorsChain.validate(transactionAuthorization);
        return new TransactionResult(account, violations);
    }
}
