import dev3.bank.dao.impl.CrudDAOImpl;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.impl.TestServiceImpl;
import dev3.bank.impl.VisitorServiceImpl;
import dev3.bank.interfaces.ClientService;
import dev3.bank.interfaces.TestService;
import dev3.bank.interfaces.VisitorService;
import menu.FabricMethod;
import menu.Menu;
import menu.Role;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        CrudDAOImpl.readEntitiesMap(getPathToDB());

        while (!exit) {
            Output.printMainMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("utils.Input number of variant: ");
            try{
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
                    case 0:
                        CrudDAOImpl.writeEntitiesMap(getPathToDB());
                        exit = true;
                        break;
                    default:
                        break;
                }
                FabricMethod fabricMethod = new FabricMethod();
                Menu menu = fabricMethod.getMenu(role);
                menu.printMenu();
            } catch (InputMismatchException e){
                System.out.println("Wrong input variant");
            }
        }
    }

    public static String getPathToDB(){
        PropertyDB propertyDB = new PropertyDB();
        return propertyDB.getProperty("database.path");
    }
}
