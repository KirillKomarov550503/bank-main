package com.netcracker.komarov.console.utils;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.entity.NewsDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.dto.entity.TransactionDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class Input {

    private static void print(String message) {
        System.out.print("\n" + message);
    }

    public static PersonDTO inputPerson(Role role) {
        Scanner scanner = new Scanner(System.in);
        PersonDTO personDTO = new PersonDTO();
        print("Input your name: ");
        String name = scanner.nextLine();
        personDTO.setName(name);
        print("Input your surname: ");
        String surname = scanner.nextLine();
        personDTO.setSurname(surname);
        print("Input your passport ID: ");
        int passportId = scanner.nextInt();
        print("Input your phone number: ");
        personDTO.setPassportId(passportId);
        int phoneNumber = scanner.nextInt();
        personDTO.setPhoneNumber(phoneNumber);
        return personDTO;
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
        print("Input account from ID: ");
        long accountFromId = scanner.nextLong();
        print("Input account to ID: ");
        long accountToId = scanner.nextLong();
        print("Input amount of money: ");
        double money = scanner.nextDouble();
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountFromId(accountFromId);
        transactionDTO.setAccountToId(accountToId);
        transactionDTO.setMoney(money);
        return transactionDTO;
    }


    public static NewsDTO inputNews() {
        Scanner scanner = new Scanner(System.in);
        NewsDTO newsDTO = new NewsDTO();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        newsDTO.setDate(simpleDateFormat.format(new Date()));

        print("Input title of article: ");
        String title = scanner.nextLine();
        newsDTO.setTitle(title);

        print("Input main text of article: ");
        String text = scanner.nextLine();
        newsDTO.setText(text);
        return newsDTO;
    }

    public static String inputStatus() {
        Scanner scanner = new Scanner(System.in);
        print("Input status of news ('general' or 'client'): ");
        return scanner.nextLine().toLowerCase();
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

    public static PersonDTO inputAdmin() {
        PersonDTO personDTO = new PersonDTO();
        Scanner scanner = new Scanner(System.in);

        print("Input name: ");
        String name = scanner.nextLine();
        personDTO.setName(name);

        print("Input surname: ");
        String surname = scanner.nextLine();
        personDTO.setSurname(surname);

        print("Input your passport ID: ");
        long passportID = scanner.nextLong();
        personDTO.setPassportId(passportID);

        print("Input your phone number: ");
        long phoneNumber = scanner.nextLong();
        personDTO.setPhoneNumber(phoneNumber);
        return personDTO;
    }

    public static long inputNewsId() {
        print("Input news ID that you want to send clients: ");
        Scanner scanner = new Scanner(System.in);
        long newsId = scanner.nextLong();
        return newsId;
    }
}
