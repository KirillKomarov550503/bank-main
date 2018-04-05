import menu.FabricMethod;
import menu.Menu;
import menu.Role;
import utils.PropertyDB;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static void printMainMenu() {
        System.out.println("1-Enter like visitor ");
        System.out.println("2-Enter like client");
        System.out.println("3-Enter like admin");
        System.out.println("0-Exit");
    }

    public static void main(String[] args) {
        boolean exit = false;
        CrudDAOImpl.readEntitiesMap(getPathToDB());

        while (!exit) {
            printMainMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input number of variant: ");
            try {
                Role role = null;
                switch (scanner.nextInt()) {
                    case 1:
                        role = Role.VISITOR;
                        break;
                    case 2:
                        role = Role.CLIENT;
                        break;
                    case 3:
                        role = Role.ADMIN;
                        break;
                    case 0:
                        CrudDAOImpl.writeEntitiesMap(getPathToDB());
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                FabricMethod fabricMethod = new FabricMethod();
                Menu menu = fabricMethod.getMenu(role);
                menu.printMenu();
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
