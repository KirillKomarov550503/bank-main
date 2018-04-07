package dev3.bank.entity;

public class Transaction extends BaseEntity {
    private String date;
    private Account accountFrom;
    private Account accountTo;
    private double money;

    @Override
    public String toString() {
        return "Transaction{" +
                "date='" + date + '\'' +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", money=" + money +
                ", id=" + id +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Account getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Account getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
