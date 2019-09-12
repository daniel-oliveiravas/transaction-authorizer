package br.com.nubank.authorizer.models;

import java.util.ArrayList;
import java.util.List;

public class TransactionResult {

    private List<String> violations;

    public TransactionResult() {
        this.violations = new ArrayList<>();
    }

    public List<String> getViolations() {
        return violations;
    }

    public void addViolation(String violation) {
        violations.add(violation);
    }
}
