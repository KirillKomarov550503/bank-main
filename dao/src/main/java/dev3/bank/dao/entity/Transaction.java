package dev3.bank.dao.entity;

import java.util.Date;

public class Transaction extends Id{
    private Date date;
    private long cardIdSender;
    private long cardIdReceiver;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCardIdSender() {
        return cardIdSender;
    }

    public void setCardIdSender(long cardIdSender) {
        this.cardIdSender = cardIdSender;
    }

    public long getCardIdReceiver() {
        return cardIdReceiver;
    }

    public void setCardIdReceiver(long cardIdReceiver) {
        this.cardIdReceiver = cardIdReceiver;
    }

    public long getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setAmountOfMoney(long amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    private long amountOfMoney;
}
