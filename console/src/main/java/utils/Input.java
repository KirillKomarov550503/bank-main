package utils;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Client;
import dev3.bank.entity.Role;

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
            System.out.println("\nWrong input phone number");
        }
        client.setRole(Role.CLIENT);
        return client;
    }

    public static long inputPersonId() {
        long personId;
        System.out.println("\nInput person ID: ");
        personId = scanner.nextLong();
        return personId;
    }

    public static int inputCardPIN() {
        int pin;
        System.out.println("\nInput pin: ");
        pin = scanner.nextInt();
        return pin;
    }

    public static long inputAccountId(){
        long accountId;
        System.out.println("\nInput account ID: ");
        accountId = scanner.nextLong();
        return accountId;
    }
}
