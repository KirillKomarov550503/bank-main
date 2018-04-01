package utils;

import dev3.bank.dto.TransactionDTO;
import dev3.bank.entity.*;

import java.text.SimpleDateFormat;
import java.util.*;

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

    public static long inputAccountId() {
        long accountId;
        System.out.println("Input account ID: ");
        accountId = scanner.nextLong();
        return accountId;
    }

    public static TransactionDTO inputTransactionDTO() {
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

    public static News createGeneralNews() {
        News news = new News();
        inputNews(news);
        news.setNewsStatus(NewsStatus.GENERAL);
        return news;

    }

    public static News createClientNews() {
        News news = new News();
        inputNews(news);
        news.setNewsStatus(NewsStatus.CLIENT);
        return news;
    }

    private static void inputNews(News news) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        news.setDate(simpleDateFormat.format(new Date()));

        print("Input title of article: ");
        String title = scanner.nextLine();
        news.setTitle(title);

        print("Input main text of article: ");
        String text = scanner.nextLine();
        news.setText(text);

        news.setAdmin(null);
    }


    public static Collection<Long> inputClientIds() {
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
        print("Input ID of card that you want to lock: ");
        long cardId = scanner.nextLong();
        return cardId;
    }
}
