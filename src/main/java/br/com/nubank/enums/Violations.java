package br.com.nubank.enums;

public enum Violations {

    ACCOUNT_ALREADY_INITIALIZED("account-already-initialized"),
    INACTIVE_CARD("card-not-active"),
    DUPLICATED_TRANSACTION("doubled-transaction"),
    HIGH_FREQUENCY("high-frequency-small-interval"),
    INSUFFICIENT_LIMIT("insufficient-limit");

    private String code;

    Violations(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
