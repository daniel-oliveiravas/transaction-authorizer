package br.com.nubank.authorizer.models;

import java.time.LocalDateTime;

public class Transaction {

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
}
