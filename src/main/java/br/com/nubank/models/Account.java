package br.com.nubank.models;

import br.com.nubank.enums.OperationType;
import com.google.gson.annotations.Expose;

public class Account extends Operation {

    @Expose
    private Integer availableLimit;

    @Expose
    private Boolean activeCard;

    public Account() {
    }

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

    @Override
    public OperationType getOperationType() {
        return OperationType.ACCOUNT_CREATION;
    }
}
