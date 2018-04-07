package entity;

public class UnlockAccountRequest extends BaseEntity {
    private Account account;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
