package br.com.nubank.authorizer.models;

import java.util.List;

public class TransactionResult {

    private Account account;
    private List<String> violations;

    public TransactionResult(Account account, List<String> violations) {
        this.account = account;
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }

    public Account getAccount() {
        return account;
    }
}
