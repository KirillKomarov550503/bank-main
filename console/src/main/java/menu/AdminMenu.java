package menu;

import dev3.bank.entity.News;
import dev3.bank.impl.AdminServiceImpl;
import dev3.bank.interfaces.AdminService;
import utils.Input;
import utils.Output;

import java.util.Collection;
import java.util.Scanner;

public class AdminMenu implements Menu {
    private AdminService adminService = new AdminServiceImpl();

    @Override
    public void printTextMenu() {
        System.out.println("1-Unlock account");
        System.out.println("2-Unlock card");
        System.out.println("3-Send general news");
        System.out.println("4-Send news to client");
        System.out.println("5-Get all clients");
        System.out.println("6-Get all accounts");
        System.out.println("7-Get all cards");
        System.out.println("8-Get all news");
        System.out.println("9-Get all client news");
        System.out.println("0-Back");
        System.out.println("\n\nInput your variant: ");
    }

    @Override
    public void printMenu() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            printTextMenu();
            switch (scanner.nextInt()) {
                case 1:
                    adminService.getAllUnlockAccountRequest().forEach(Output::printAccount);
                    adminService.unlockAccount(Input.inputClientId());
                    break;
                case 2:
                    adminService.getAllUnlockCardRequest().forEach(Output::printCard);
                    adminService.unlockCard(Input.inputCardId());
                    break;
                case 3:
                    adminService.addGeneralNews(Input.createGeneralNews());
                    break;
                case 4:
                    News news = Input.createClientNews();
                    Collection<Long> clientIds = Input.inputClientIds();
                    adminService.addClientNews(clientIds, news);
                    break;
                case 5:
                    adminService.getAllClients().forEach(System.out::println);
                    break;
                case 6:
                    adminService.getAllAccounts().forEach(System.out::println);
                    break;
                case 7:
                    adminService.getAllCards().forEach(System.out::println);
                    break;
                case 8:
                    adminService.getAllGeneralNews().forEach(System.out::println);
                    break;
                case 9:
                    adminService.getAllClientNews().forEach(System.out::println);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
