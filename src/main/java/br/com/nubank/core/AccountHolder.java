package br.com.nubank.core;

import br.com.nubank.models.Account;

public class AccountHolder {

    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
