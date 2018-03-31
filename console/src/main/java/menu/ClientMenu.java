package menu;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.Transaction;
import dev3.bank.exception.TransactionException;
import dev3.bank.impl.ClientServiceImpl;
import dev3.bank.interfaces.ClientService;
import utils.Input;
import utils.Output;

import java.util.Collection;
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
        System.out.println("8-Show transaction story");
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
                    Collection<Account> accounts1 = clientService.getUnlockAccounts(Input.inputClientId());
                    Output.printAccountCollection(accounts1);
                    clientService.lockAccount(Input.inputAccountId());
                    break;

                case 5:
                    Collection<Card> cards1 = clientService.getUnlockCards(Input.inputClientId());
                    Output.printCardCollection(cards1);
                    clientService.lockCard(Input.inputCardId());
                    break;
                case 6:
                    Collection<Account> accounts2 = clientService.getLockAccounts(Input.inputClientId());
                    Output.printAccountCollection(accounts2);
                    clientService.unlockAccountRequest(Input.inputAccountId());
                    break;
                case 7:
                    Collection<Card> cards2 = clientService.getUnlockCards(Input.inputCardId());
                    Output.printCardCollection(cards2);
                    clientService.unlockCardRequest(Input.inputCardId());
                    break;
                case 8:
                    Collection<Transaction> transactions = clientService.showStories(Input.inputClientId());
                    Output.printTransactionStory(transactions);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
