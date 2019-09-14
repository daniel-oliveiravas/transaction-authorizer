package br.com.nubank.models;

import java.util.ArrayList;
import java.util.List;

public class TransactionResult {

    private Account account;
    private List<String> violations;

    public TransactionResult(Account account, List<String> violations) {
        this.account = account;
        this.violations = violations;
    }

    public TransactionResult(Account account) {
        this.account = account;
        this.violations = new ArrayList<>();
    }

    public List<String> getViolations() {
        return violations;
    }

    public Account getAccount() {
        return account;
    }

    public void addViolation(String violation) {
        violations.add(violation);
    }
}
