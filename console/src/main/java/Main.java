import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.impl.TestServiceImpl;
import dev3.bank.impl.VisitorServiceImpl;
import dev3.bank.interfaces.ClientService;
import dev3.bank.interfaces.TestService;
import dev3.bank.interfaces.VisitorService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean exit = false;
        Output.printMenu();
        while (!exit) {
            Scanner scanner = new Scanner(System.in);

            switch (scanner.nextInt()) {
                case 1:
                    VisitorService visitorService = new VisitorServiceImpl();
                    visitorService.registration(Input.inputClientData());
                    break;
                case 2:
                    TestService testService = new TestServiceImpl();
                    Output.printClients(testService.getAll());
                case 0:

                    exit = true;
                    break;
                default:
                    break;
            }
        }
    }
}
