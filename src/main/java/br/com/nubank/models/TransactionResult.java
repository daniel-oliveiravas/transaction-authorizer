package br.com.nubank.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class TransactionResult {

    @Expose
    private Account account;

    @Expose
    private List<String> violations;

    public TransactionResult(Account account, List<String> violations) {
        this.account = account;
        this.violations = violations;
    }

    public List<String> getViolations() {
        return violations;
    }

}
