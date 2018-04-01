package menu;

import dev3.bank.entity.News;
import dev3.bank.impl.AdminServiceImpl;
import dev3.bank.interfaces.AdminService;
import utils.Input;
import utils.Output;

import javax.print.DocFlavor;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class AdminMenu implements Menu {
    private AdminService adminService = new AdminServiceImpl();

    @Override
    public void printTextMenu() {
        System.out.println("1-Unlock account");
        System.out.println("2-Unlock card");
        System.out.println("3-Send general news");
        System.out.println("4-Send news to client");
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
                case 3:
                    adminService.addGeneralNews(Input.createGeneralNews());
                    break;
                case 4:
                    News news = Input.createClientNews();
                    Collection<Long> clientIds = Input.inputClientIds();
                    adminService.addClientNews(clientIds, news);
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
