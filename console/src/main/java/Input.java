import dev3.bank.entity.Client;
import dev3.bank.entity.Role;

import java.util.Scanner;

public class Input {
    private static Scanner scanner = new Scanner(System.in);

    private static void print(String message){
        System.out.print("\n" + message);
    }

    public static Client inputClientData(){
        Client client = new Client();
        System.out.print("Input your name: ");
        client.setName(scanner.nextLine());
        print("Input your surname: ");
        client.setSurname(scanner.nextLine());
        print("Input your phone number: ");
        try{
            client.setPhoneNumber(scanner.nextInt());
        } catch (NumberFormatException e){
            System.out.println("\nWrong input phone number");
        }
        client.setRole(Role.CLIENT);
        return client;
    }
}
