package menu;

import dev3.bank.entity.News;
import dev3.bank.impl.AdminServiceImpl;
import dev3.bank.interfaces.AdminService;
import utils.Input;

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
        System.out.println("10-Get all unlock account requests");
        System.out.println("11-Get all unlock card requests");
        System.out.println("12-Add new admin");
        System.out.println("13-Get all admins");
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
                    adminService.getAllUnlockAccountRequest().forEach(System.out::println);
                    adminService.unlockAccount(Input.inputAccountId());
                    break;
                case 2:
                    adminService.getAllUnlockCardRequest().forEach(System.out::println);
                    adminService.unlockCard(Input.inputCardId());
                    break;
                case 3:
                    adminService.addGeneralNews(Input.createGeneralNews(), Input.inputAdminId());
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
                case 10:
                    adminService.getAllUnlockAccountRequest().forEach(System.out::println);
                    break;
                case 11:
                    adminService.getAllUnlockCardRequest().forEach(System.out::println);
                    break;
                case 12:
                    adminService.addAdmin(Input.inputAdmin());
                    break;
                case 13:
                    adminService.getAllAdmin().forEach(System.out::println);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
