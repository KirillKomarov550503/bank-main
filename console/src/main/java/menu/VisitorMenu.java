package menu;

import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.impl.VisitorServiceImpl;
import dev3.bank.interfaces.VisitorService;
import utils.Input;
import utils.Output;

import java.util.Scanner;

public class VisitorMenu implements Menu {
    private VisitorService visitorService;

    @Override
    public void printTextMenu() {
        System.out.println("1-Get all news");
        System.out.println("2-Registration");
        System.out.println("0-Back to main menu");
        System.out.println("\nInput your variant: ");
    }

    @Override
    public void initService() {
        visitorService = VisitorServiceImpl.getVisitorService();
        visitorService.setDAO(PostgreSQLDAOFactory.getPostgreSQLDAOFactory());
    }

    @Override
    public void printMenu() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        initService();
        while (flag) {
            printTextMenu();
            switch (scanner.nextInt()) {
                case 1:
                    visitorService.getAllNews().forEach(Output::printNews);
                    break;
                case 2:
                    visitorService.registration(Input.inputPerson(dev3.bank.entity.Role.CLIENT));
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
