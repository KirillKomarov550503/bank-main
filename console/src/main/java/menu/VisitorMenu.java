package menu;

import dev3.bank.dao.impl.ClientDAOImpl;
import dev3.bank.dao.impl.NewsDAOImpl;
import dev3.bank.dao.impl.PersonDAOImpl;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Role;
import dev3.bank.impl.VisitorServiceImpl;
import dev3.bank.interfaces.VisitorService;
import utils.Input;
import utils.Output;

import java.sql.Connection;
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
        Connection connection = DataBase.getConnection();
        ((VisitorServiceImpl) visitorService).setClientDAO(ClientDAOImpl.getClientDAO(connection));
        ((VisitorServiceImpl) visitorService).setNewsDAO(NewsDAOImpl.getNewsDAO(connection));
        ((VisitorServiceImpl) visitorService).setPersonDAO(PersonDAOImpl.getPersonDAO(connection));
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
