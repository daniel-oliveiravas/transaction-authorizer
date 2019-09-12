package br.com.nubank.authorizer.models;

public class Account {

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
}
