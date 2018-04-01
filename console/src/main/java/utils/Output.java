package utils;

import dev3.bank.entity.*;

import java.sql.SQLOutput;
import java.util.Collection;

public class Output {

    public static void printNews(News news) {
        System.out.println("" +
                "Date: " + news.getDate() + "\t" +
                "Title: " + news.getTitle() + "\t" +
                "Text: " + news.getText());
    }

    public static void printAccount(Account account) {
        System.out.print("\n\nID: " + account.getId() + "\t");
        System.out.print("Account status: ");
        if (account.isLocked())
            System.out.print("lock");
        else
            System.out.print("unlock");
        System.out.print("\tBalance: " + account.getBalance());
    }

    public static void printCard(Card card) {
        System.out.print("\n\nID: " + card.getId() + "\t");
        System.out.print("Card status: ");
        if (card.isLocked())
            System.out.print("lock");
        else
            System.out.print("unlock");
        System.out.print("\nPIN: " + card.getPin());
    }

    public static void printTransaction(Transaction transaction) {
        System.out.print("\n\nID: " + transaction.getId() + "\t");
        System.out.print("Account from ID: " + transaction.getAccountFrom().getId() + "\t");
        System.out.print("Account to ID: " + transaction.getAccountTo().getId() + "\t");
        System.out.print("Money: " + transaction.getMoney() + "\t");
        System.out.print("Date of transaction: " + transaction.getDate());
    }
}
