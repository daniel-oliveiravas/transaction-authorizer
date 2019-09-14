package br.com.nubank.models;

import br.com.nubank.enums.OperationType;

import java.util.ArrayList;
import java.util.List;

public class Account extends Operation {

    private Integer availableLimit;
    private Boolean activeCard;
    private List<Transaction> history;

    public Account(Integer availableLimit, Boolean activeCard) {
        this.availableLimit = availableLimit;
        this.activeCard = activeCard;
        this.history = new ArrayList<>();
    }

    public Integer getAvailableLimit() {
        return availableLimit;
    }

    public Boolean getActiveCard() {
        return activeCard;
    }

    public void setAvailableLimit(Integer availableLimit) {
        this.availableLimit = availableLimit;
    }

    public void addTransaction(Transaction transaction) {
        this.history.add(transaction);
    }

    public List<Transaction> getHistory() {
        return history;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATION;
    }
}
