package com.netcracker.komarov.console.menu;

import com.netcracker.komarov.console.utils.Input;
import com.netcracker.komarov.dao.entity.NewsStatus;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Scanner;

@Component
public class AdminMenu implements Menu {
    private AdminService adminService;
    private AccountService accountService;
    private CardService cardService;
    private ClientService clientService;
    private NewsService newsService;
    private ClientNewsService clientNewsService;
    private PersonService personService;

    @Autowired
    public AdminMenu(AdminService adminService, AccountService accountService,
                     CardService cardService, ClientService clientService,
                     NewsService newsService, ClientNewsService clientNewsService, PersonService personService) {
        this.adminService = adminService;
        this.accountService = accountService;
        this.cardService = cardService;
        this.clientService = clientService;
        this.newsService = newsService;
        this.clientNewsService = clientNewsService;
        this.personService = personService;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1-Unlock account");
        System.out.println("2-Unlock card");
        System.out.println("3-Add news");
        System.out.println("4-Send news to client");
        System.out.println("5-Get all people");
        System.out.println("6-Get all clients");
        System.out.println("7-Get all accounts");
        System.out.println("8-Get all cards");
        System.out.println("9-Get all news");
        System.out.println("10-Get all client news");
        System.out.println("11-Get all requests to unlock account");
        System.out.println("12-Get all requests to unlock card");
        System.out.println("13-Add new admin");
        System.out.println("14-Get all admins");
        System.out.println("0-Back");
        System.out.println("\n\nInput your variant: ");
    }

    @Override
    public void printMenu() {
        boolean flag = true;
        Scanner scanner = new Scanner(System.in);
        while (flag) {
            printTextMenu();
            switch (scanner.nextInt()) {
                case 1:
                    accountService.getAllUnlockAccountRequest().forEach(System.out::println);
                    accountService.unlockAccount(Input.inputAccountId());
                    break;
                case 2:
                    cardService.getAllUnlockCardRequest().forEach(System.out::println);
                    cardService.unlockCard(Input.inputCardId());
                    break;
                case 3:
                    newsService.addGeneralNews(Input.inputNews(), Input.inputAdminId());
                    break;
                case 4:
                    newsService.getAllNewsByStatus(NewsStatus.CLIENT).forEach(System.out::println);
                    long newsId = Input.inputNewsId();
                    Collection<Long> clientIds = Input.inputClientIds();
                    clientNewsService.addClientNews(clientIds, newsId);
                    break;
                case 5:
                    personService.getAllPeople().forEach(System.out::println);
                    break;
                case 6:
                    clientService.getAllClients().forEach(System.out::println);
                    break;
                case 7:
                    accountService.getAllAccounts().forEach(System.out::println);
                    break;
                case 8:
                    cardService.getAllCards().forEach(System.out::println);
                    break;
                case 9:
                    newsService.getAllGeneralNews().forEach(System.out::println);
                    break;
                case 10:
                    clientNewsService.getAllClientNews().forEach(System.out::println);
                    break;
                case 11:
                    accountService.getAllUnlockAccountRequest().forEach(System.out::println);
                    break;
                case 12:
                    cardService.getAllUnlockCardRequest().forEach(System.out::println);
                    break;
                case 13:
                    adminService.addAdmin(Input.inputPerson(Role.ADMIN));
                    break;
                case 14:
                    adminService.getAllAdmin().forEach(System.out::println);
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
