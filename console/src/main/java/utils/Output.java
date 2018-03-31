package utils;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Client;
import dev3.bank.entity.Transaction;

import java.sql.SQLOutput;
import java.util.Collection;

public class Output {

    public static void printAccountCollection(Collection<Account> accounts) {
        for (Account account : accounts) {
            System.out.print("\n\nID: " + account.getId() + "\t");
            System.out.print("Account status: ");
            if (account.isLocked())
                System.out.print("lock");
            else
                System.out.print("unlock");
            System.out.print("\tBalance: " + account.getBalance());
        }
    }

    public static void printCardCollection(Collection<Card> cards) {
        for (Card card : cards) {
            System.out.print("\n\nID: " + card.getId() + "\t");
            System.out.print("Card status: ");
            if (card.isLocked())
                System.out.print("lock");
            else
                System.out.print("unlock");
            System.out.print("\nPIN: " + card.getPin());
        }
    }

    public static void printTransactionStory(Collection<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.print("\n\nID: " + transaction.getId() + "\t");
            System.out.print("Account from ID: " + transaction.getAccountFrom().getId() + "\t");
            System.out.print("Account to ID: " + transaction.getAccountTo().getId() + "\t");
            System.out.print("Money: " + transaction.getMoney() + "\t");
            System.out.print("Date of transaction: " + transaction.getDate());

        }
    }
}
