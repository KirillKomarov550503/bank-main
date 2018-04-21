import dev3.bank.dao.utils.DataBase;
import dev3.bank.dao.utils.PropertyDB;
import dev3.bank.AppContext;
import dev3.bank.interfaces.AdminService;
import menu.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.InputMismatchException;
import java.util.Scanner;

@ComponentScan(basePackages = "menu")
public class Main {
    private AdminMenu adminMenu;
    private ClientMenu clientMenu;
    private VisitorMenu visitorMenu;

    private static void printMainMenu() {
        System.out.println("1-Enter like visitor ");
        System.out.println("2-Enter like client");
        System.out.println("3-Enter like admin");
        System.out.println("0-Exit");
    }

    private void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        adminMenu = context.getBean(AdminMenu.class);
        clientMenu = context.getBean(ClientMenu.class);
        visitorMenu = context.getBean(VisitorMenu.class);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.enterLike();
    }

    private void enterLike() {
        init();
        DataBase.executeProperty("init.table.path", "dao\\src\\main\\resources\\path.properties");
        while (true) {
            printMainMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input number of variant: ");
            Menu menu = null;
            try {
                switch (scanner.nextInt()) {
                    case 1:
                        menu = visitorMenu;
                        break;
                    case 2:
                        menu = clientMenu;
                        break;
                    case 3:
                        menu = adminMenu;
                        break;
                    case 0:
                        DataBase.closeConnection();
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                if (menu != null) {
                    menu.printMenu();
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input variant");
            }
        }
    }

    private static String getPathToDB() {
        PropertyDB propertyDB = new PropertyDB();
        return propertyDB.getProperty("database.path");
    }
}
