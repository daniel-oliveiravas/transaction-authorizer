package br.com.nubank.models;

import br.com.nubank.enums.OperationType;

import java.time.LocalDateTime;

public class Transaction extends Operation {

    private String merchant;
    private Integer amount;
    private LocalDateTime time;

    public Transaction(String merchant, Integer amount, LocalDateTime time) {
        this.merchant = merchant;
        this.amount = amount;
        this.time = time;
    }

    public String getMerchant() {
        return merchant;
    }

    public Integer getAmount() {
        return amount;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.TRANSACTION;
    }
}
