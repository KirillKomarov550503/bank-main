package com.netcracker.komarov.console.menu;

import com.netcracker.komarov.console.utils.Input;
import com.netcracker.komarov.console.utils.Output;
import com.netcracker.komarov.console.utils.Producer;
import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.services.exception.TransactionException;
import com.netcracker.komarov.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ClientMenu implements Menu {
    private AccountService accountService;
    private CardService cardService;
    private NewsService newsService;
    private TransactionService transactionService;
    private RequestService requestService;

    @Autowired
    public ClientMenu(AccountService accountService, CardService cardService, NewsService newsService,
                      TransactionService transactionService, RequestService requestService) {
        this.accountService = accountService;
        this.cardService = cardService;
        this.newsService = newsService;
        this.transactionService = transactionService;
        this.requestService = requestService;
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
    public void printMenu() {
        boolean flag = true;
        try (Scanner scanner = new Scanner(System.in);) {
            while (flag) {
                printTextMenu();
                switch (scanner.nextInt()) {
                    case 1:
                        Account account = Producer.produceAccount();
                        accountService.createAccount(account, Input.inputClientId());
                        break;
                    case 2:
                        Card card = Producer.produceCard();
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
                        requestService.saveRequest(Input.inputAccountId(), RequestStatus.ACCOUNT);
                        break;
                    case 7:
                        cardService.getLockCards(Input.inputClientId()).forEach(Output::printCard);
                        requestService.saveRequest(Input.inputCardId(), RequestStatus.ACCOUNT);
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
}
