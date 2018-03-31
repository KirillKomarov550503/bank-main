package utils;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.Account;
import dev3.bank.entity.Client;
import dev3.bank.entity.Role;
import dev3.bank.entity.Transaction;

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
        System.out.println("Input your ID: ");
        personId = scanner.nextLong();
        return personId;
    }

    public static int inputCardPIN() {
        int pin;
        System.out.println("Come up with PIN of your card: ");
        pin = scanner.nextInt();
        return pin;
    }

    public static long inputAccountId(){
        long accountId;
        System.out.println("Input account ID: ");
        accountId = scanner.nextLong();
        return accountId;
    }

    public static TransactionDTO inputTransactionDTO(){
        print("Input your client ID: ");
        long clientId = scanner.nextLong();
        print("Input account from ID: ");
        long accountFromId = scanner.nextLong();
        print("Input account to ID: ");
        long accountToId = scanner.nextLong();
        print("Input amount of money: ");
        double money = scanner.nextDouble();
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountFromId(accountFromId);
        transactionDTO.setAccountToId(accountToId);
        transactionDTO.setClientId(clientId);
        transactionDTO.setMoney(money);
        return transactionDTO;
    }

    public static long inputCardId() {
        print("Input ID of card that you want to lock: ");
        long cardId = scanner.nextLong();
        return cardId;
    }
}
