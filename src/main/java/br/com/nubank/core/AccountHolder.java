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

    public void setNewAvailableLimit(Integer newAvailableLimit) {
        this.account = new Account(newAvailableLimit, this.account.getActiveCard());
    }
}
