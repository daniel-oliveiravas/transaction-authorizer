package br.com.nubank.models;

import br.com.nubank.enums.OperationType;

public class Account extends Operation {

    private Integer availableLimit;
    private Boolean activeCard;

    public Account(Integer availableLimit, Boolean activeCard) {
        this.availableLimit = availableLimit;
        this.activeCard = activeCard;
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

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATION;
    }
}
