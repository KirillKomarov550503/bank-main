package utils;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class Input {

    private static void print(String message) {
        System.out.print("\n" + message);
    }

    public static Person inputPerson(Role role) {
        Scanner scanner = new Scanner(System.in);
        Person person = new Person();
        print("Input your name: ");
        String name = scanner.nextLine();
        person.setName(name);
        print("Input your surname: ");
        String surname = scanner.nextLine();
        person.setSurname(surname);
        print("Input your passport ID: ");
        int passportId = scanner.nextInt();
        print("Input your phone number: ");
        person.setPassportId(passportId);
        int phoneNumber = scanner.nextInt();
        try {
            person.setPhoneNumber(phoneNumber);
        } catch (NumberFormatException e) {
            System.out.println("Wrong input phone number");
        }
        person.setRole(role);
        return person;
    }

    public static long inputClientId() {
        Scanner scanner = new Scanner(System.in);
        long personId;
        System.out.println("Input client ID: ");
        personId = scanner.nextLong();
        return personId;
    }

    public static int inputCardPIN() {
        Scanner scanner = new Scanner(System.in);
        int pin;
        System.out.println("Come up with PIN of your card: ");
        pin = scanner.nextInt();
        return pin;
    }

    public static long inputAccountId() {
        Scanner scanner = new Scanner(System.in);
        long accountId;
        System.out.println("Input account ID: ");
        accountId = scanner.nextLong();
        return accountId;
    }

    public static TransactionDTO inputTransactionDTO() {
        Scanner scanner = new Scanner(System.in);
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


    public static News inputNews() {
        Scanner scanner = new Scanner(System.in);
        News news = new News();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        news.setDate(simpleDateFormat.format(new Date()));

        print("Input title of article: ");
        String title = scanner.nextLine();
        news.setTitle(title);

        print("Input main text of article: ");
        String text = scanner.nextLine();
        news.setText(text);

        print("Input symbol of news status ('general' or 'client'): ");
        String status = scanner.nextLine().toLowerCase();
        if (status.equals("general")) {
            news.setNewsStatus(NewsStatus.GENERAL);
        } else if (status.equals("client")) {
            news.setNewsStatus(NewsStatus.CLIENT);
        }
        return news;
    }


    public static Collection<Long> inputClientIds() {
        Scanner scanner = new Scanner(System.in);
        Collection<Long> clientIds = new ArrayList<>();
        print("\nInput IDs more than 1 to send news to concrete clients or " +
                "input 0 to send news to all clients. To finish input press empty line. ");
        boolean flag = true;
        while (flag) {
            print("Input client ID: ");
            String clientId = scanner.nextLine();
            if (clientId.isEmpty()) {
                flag = false;
            } else {
                clientIds.add(Long.valueOf(clientId));
            }
        }
        return clientIds;
    }

    public static long inputCardId() {
        Scanner scanner = new Scanner(System.in);
        print("Input ID of card that you want to lock: ");
        long cardId = scanner.nextLong();
        return cardId;
    }

    public static long inputAdminId() {
        Scanner scanner = new Scanner(System.in);
        print("Input admin ID: ");
        long adminId = scanner.nextLong();
        return adminId;
    }

    public static Person inputAdmin() {
        Person person = new Person();
        Scanner scanner = new Scanner(System.in);

        print("Input name: ");
        String name = scanner.nextLine();
        person.setName(name);

        print("Input surname: ");
        String surname = scanner.nextLine();
        person.setSurname(surname);

        print("Input your passport ID: ");
        long passportID = scanner.nextLong();
        person.setPassportId(passportID);

        print("Input your phone number: ");
        long phoneNumber = scanner.nextLong();
        person.setPhoneNumber(phoneNumber);
        return person;
    }

    public static long inputNewsId() {
        print("Input news ID that you want to send clients: ");
        Scanner scanner = new Scanner(System.in);
        long newsId = scanner.nextLong();
        return newsId;
    }
}
