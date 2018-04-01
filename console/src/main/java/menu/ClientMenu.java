package menu;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.exception.TransactionException;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.interfaces.ClientService;
import utils.Input;
import utils.Output;

import java.util.Scanner;

public class ClientMenu implements Menu {
    private ClientService clientService = new ClientServiceImpl();

    @Override
    public void printTextMenu() {
        System.out.println("1-Create bank account");
        System.out.println("2-Create bank card");
        System.out.println("3-Create transaction");
        System.out.println("4-Lock account");
        System.out.println("5-Lock card");
        System.out.println("6-Send request to unlock account");
        System.out.println("7-Send request to unlock card");
        System.out.println("8-View transaction story");
        System.out.println("9-View bank messages");
        System.out.println("0-Back");
        System.out.println("\nInput your variant: ");
    }

    @Override
    public void printMenu() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            printTextMenu();
            switch (scanner.nextInt()) {
                case 1:
                    Account account = new Account();
                    account.setBalance(0.0);
                    account.setLocked(false);
                    clientService.createAccount(account, Input.inputClientId());
                    break;
                case 2:
                    Card card = new Card();
                    card.setLocked(false);
                    card.setPin(Input.inputCardPIN());
                    clientService.createCard(card, Input.inputAccountId());
                    break;
                case 3:
                    try {
                        clientService.createTransaction(Input.inputTransactionDTO());
                    } catch (TransactionException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    clientService.getUnlockAccounts(Input.inputClientId()).forEach(Output::printAccount);
                    clientService.lockAccount(Input.inputAccountId());
                    break;

                case 5:
                    clientService.getUnlockCards(Input.inputClientId()).forEach(Output::printCard);
                    clientService.lockCard(Input.inputCardId());
                    break;
                case 6:
                    clientService.getLockAccounts(Input.inputClientId()).forEach(Output::printAccount);
                    clientService.unlockAccountRequest(Input.inputAccountId());
                    break;
                case 7:
                    clientService.getUnlockCards(Input.inputCardId()).forEach(Output::printCard);
                    clientService.unlockCardRequest(Input.inputCardId());
                    break;
                case 8:
                    clientService.showStories(Input.inputClientId()).forEach(Output::printTransaction);
                    break;
                case 9:
                    clientService.getAllPersonalNews(Input.inputClientId()).forEach(Output::printNews);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
