package menu;

import dev3.bank.entity.Account;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.interfaces.ClientService;
import utils.Input;

import java.util.ArrayList;
import java.util.Scanner;

public class ClientMenu implements Menu {
    private ClientService clientService = new ClientServiceImpl();

    private void printText() {
        System.out.println("1-Create bank account");
        System.out.println("2-Create bank card");
        System.out.println("3-Create transaction");
        System.out.println("4-Lock account");
        System.out.println("5-Lock card");
        System.out.println("6-Send request to unlock account");
        System.out.println("7-Send request to unlock card");
        System.out.println("8-Show transaction story");
        System.out.println("0-Back");
    }

    @Override
    public void printMenu() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            switch (scanner.nextInt()) {
                case 1:
                    Account account = new Account();
                    account.setBalance(0.0);
                    account.setCardCollection(new ArrayList<>());
                    account.setLocked(false);
                    clientService.createAccount(account, Input.inputPersonId());
                case 2:

                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
