import dev3.bank.dao.impl.CrudDAOImpl;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.impl.TestServiceImpl;
import dev3.bank.impl.VisitorServiceImpl;
import dev3.bank.interfaces.ClientService;
import dev3.bank.interfaces.TestService;
import dev3.bank.interfaces.VisitorService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        CrudDAOImpl.readEntitiesMap(getPathToDB());

        while (!exit) {
            Output.printMenu();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input number of variant: ");
            try{
                switch (scanner.nextInt()) {
                    case 1:
                        VisitorService visitorService = new VisitorServiceImpl();
                        visitorService.registration(Input.inputClientData());
                        break;
                    case 2:
                        TestService testService = new TestServiceImpl();
                        Output.printClients(testService.getAll());
                        break;
                    case 0:
                        CrudDAOImpl.writeEntitiesMap(getPathToDB());
                        exit = true;
                        break;
                    default:
                        break;
                }
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
