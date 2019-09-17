package br.com.nubank.authorizer;

import br.com.nubank.authorizer.interfaces.Authorizer;
import br.com.nubank.authorizer.validators.chain.BasicValidatorsChain;
import br.com.nubank.authorizer.validators.chain.ValidatorsChain;
import br.com.nubank.models.Account;
import br.com.nubank.models.Transaction;

import java.util.List;

public class BasicAuthorizer implements Authorizer {

    private ValidatorsChain validatorsChain;

    public BasicAuthorizer() {
        this.validatorsChain = new BasicValidatorsChain();
    }

    @Override
    public List<String> authorizeTransaction(Account account, Transaction transaction,
                                             List<Transaction> history) {
        return validatorsChain.validate(account, transaction, history);
    }
}
