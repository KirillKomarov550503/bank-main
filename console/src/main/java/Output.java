import dev3.bank.entity.Client;

import java.util.Collection;

public class Output {
    public static void printMenu() {
        System.out.println("1-Add client");
        System.out.println("2-Get all clients");
        System.out.println("0-Exit");
    }

    public static void printClients(Collection<Client> clients) {
        for (Client client : clients) {
            System.out.print("\n" +
                    "ID: " + client.getId() + "\t" +
                    "Name: " + client.getName() + "\t" +
                    "Surname: " + client.getSurname() + "\t" +
                    "Phone number: " + client.getPhoneNumber() +  "\n");
        }
    }
}
