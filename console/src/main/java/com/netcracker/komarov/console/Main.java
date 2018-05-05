package com.netcracker.komarov.console;

import com.netcracker.komarov.console.menu.AdminMenu;
import com.netcracker.komarov.console.menu.ClientMenu;
import com.netcracker.komarov.console.menu.Menu;
import com.netcracker.komarov.console.menu.VisitorMenu;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.dao.utils.PropertyDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.InputMismatchException;
import java.util.Scanner;

@ComponentScan(basePackages = {"com.netcracker.komarov.console", "com.netcracker.komarov.services"})
@SpringBootApplication
public class Main implements CommandLineRunner {
    private AdminMenu adminMenu;
    private ClientMenu clientMenu;
    private VisitorMenu visitorMenu;

    @Autowired
    public Main(AdminMenu adminMenu, ClientMenu clientMenu, VisitorMenu visitorMenu) {
        this.adminMenu = adminMenu;
        this.clientMenu = clientMenu;
        this.visitorMenu = visitorMenu;
    }

    private static void printMainMenu() {
        System.out.println("1-Enter like visitor ");
        System.out.println("2-Enter like client");
        System.out.println("3-Enter like admin");
        System.out.println("0-Exit");
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    private void enterLike() {
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
                        scanner.close();
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

    @Override
    public void run(String... args) throws Exception {
        enterLike();
    }
}
