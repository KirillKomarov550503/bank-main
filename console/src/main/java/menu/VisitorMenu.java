package menu;

import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.impl.NewsServiceImpl;
import dev3.bank.interfaces.ClientService;
import dev3.bank.interfaces.NewsService;
import utils.Input;
import utils.Output;

import java.util.Scanner;

public class VisitorMenu implements Menu {
    private NewsService newsService;
    private ClientService clientService;

    public VisitorMenu() {
        newsService = NewsServiceImpl.getNewsService();
        clientService = ClientServiceImpl.getClientService();
    }

    @Override
    public void printTextMenu() {
        System.out.println("1-Get all news");
        System.out.println("2-Registration");
        System.out.println("0-Back to main menu");
        System.out.println("\nInput your variant: ");
    }

    @Override
    public void initService() {
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        newsService.setDAO(daoFactory);
        clientService.setDAO(daoFactory);
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
                    newsService.getAllNews().forEach(Output::printNews);
                    break;
                case 2:
                    clientService.registration(Input.inputPerson(dev3.bank.entity.Role.CLIENT));
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
