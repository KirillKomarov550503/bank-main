package utils;

import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.entity.*;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    private static void print(String message) {
        System.out.print("\n" + message);
    }

    public static Client inputClient() {
        Client client = new Client();
        print("Input your name: ");
        client.setName(scanner.nextLine());
        print("Input your surname: ");
        client.setSurname(scanner.nextLine());
        print("Input your phone number: ");
        try {
            client.setPhoneNumber(scanner.nextInt());
        } catch (NumberFormatException e) {
            System.out.println("Wrong input phone number");
        }
        client.setRole(Role.CLIENT);
        return client;
    }

    public static long inputClientId() {
        long personId;
        System.out.println("Input person ID: ");
        personId = scanner.nextLong();
        return personId;
    }

    public static int inputCardPIN() {
        int pin;
        System.out.println("Input pin: ");
        pin = scanner.nextInt();
        return pin;
    }

    public static long inputAccountId(){
        long accountId;
        System.out.println("Input account ID: ");
        accountId = scanner.nextLong();
        return accountId;
    }

    public static Transaction inputTransaction(){
        Transaction transaction = new Transaction();
        print("Input account ID FROM you want transfer money: ");
        long accountFromId = scanner.nextLong();
        print("Input account ID WHERE you want to transfer money: ");
        long accountToId = scanner.nextLong();
        print("Input amount of transfer: ");
        double money = scanner.nextDouble();
        Account accountFrom = new Account();
        accountFrom.setId(accountFromId);
        Account accountTo = new Account();
        accountTo.setId(accountToId);
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setMoney(money);
        return transaction;
    }

}
