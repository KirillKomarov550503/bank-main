package menu;

import dev3.bank.entity.Account;
import dev3.bank.entity.Card;
import dev3.bank.entity.UnlockAccountRequest;
import dev3.bank.exception.TransactionException;
import dev3.bank.factory.DAOFactory;
import dev3.bank.factory.PostgreSQLDAOFactory;
import dev3.bank.impl.*;
import dev3.bank.interfaces.*;
import utils.Input;
import utils.Output;

import java.util.Scanner;

public class ClientMenu implements Menu {
    private AccountService accountService;
    private CardService cardService;
    private NewsService newsService;
    private TransactionService transactionService;
    private UnlockAccountRequestService unlockAccountRequestService;
    private UnlockCardRequestService unlockCardRequestService;

    public ClientMenu() {
        accountService = AccountServiceImpl.getAccountService();
        cardService = CardServiceImpl.getCardService();
        newsService = NewsServiceImpl.getNewsService();
        transactionService = TransactionServiceImpl.getTransactionService();
        unlockAccountRequestService = UnlockAccountRequestServiceImpl.getUnlockAccountRequestService();
        unlockCardRequestService = UnlockCardRequestServiceImpl.getUnlockCardRequestService();
    }

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
        System.out.println("10-Refill by 100 conventional units");
        System.out.println("0-Back");
        System.out.println("\nInput your variant: ");
    }


    @Override
    public void initService() {
        DAOFactory daoFactory = PostgreSQLDAOFactory.getPostgreSQLDAOFactory();
        accountService.setDAO(daoFactory);
        cardService.setDAO(daoFactory);
        newsService.setDAO(daoFactory);
        transactionService.setDAO(daoFactory);
        unlockCardRequestService.setDAO(daoFactory);
        unlockAccountRequestService.setDAO(daoFactory);
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
                    Account account = new Account();
                    account.setBalance(0.0);
                    account.setLocked(false);
                    accountService.createAccount(account, Input.inputClientId());
                    break;
                case 2:
                    Card card = new Card();
                    card.setLocked(false);
                    card.setPin(Input.inputCardPIN());
                    cardService.createCard(card, Input.inputAccountId());
                    break;
                case 3:
                    try {
                        transactionService.createTransaction(Input.inputTransactionDTO());
                    } catch (TransactionException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    accountService.getUnlockAccounts(Input.inputClientId()).forEach(Output::printAccount);
                    accountService.lockAccount(Input.inputAccountId());
                    break;

                case 5:
                    cardService.getUnlockCards(Input.inputClientId()).forEach(Output::printCard);
                    cardService.lockCard(Input.inputCardId());
                    break;
                case 6:
                    accountService.getLockAccounts(Input.inputClientId()).forEach(Output::printAccount);
                    unlockAccountRequestService.unlockAccountRequest(Input.inputAccountId());
                    break;
                case 7:
                    cardService.getLockCards(Input.inputClientId()).forEach(Output::printCard);
                    unlockCardRequestService.unlockCardRequest(Input.inputCardId());
                    break;
                case 8:
                    transactionService.showStories(Input.inputClientId()).forEach(Output::printTransaction);
                    break;
                case 9:
                    newsService.getAllPersonalNews(Input.inputClientId()).forEach(Output::printNews);
                    break;
                case 10:
                    accountService.refill(Input.inputAccountId());
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
