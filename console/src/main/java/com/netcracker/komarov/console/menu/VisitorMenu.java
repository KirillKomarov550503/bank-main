package com.netcracker.komarov.console.menu;

import com.netcracker.komarov.console.utils.Input;
import com.netcracker.komarov.console.utils.Output;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class VisitorMenu implements Menu {
    private NewsService newsService;
    private ClientService clientService;

    @Autowired
    public VisitorMenu(NewsService newsService, ClientService clientService) {
        this.newsService = newsService;
        this.clientService = clientService;
    }

    @Override
    public void printTextMenu() {
        System.out.println("1-Get all news");
        System.out.println("2-Registration");
        System.out.println("0-Back to main menu");
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
                    newsService.getAllNews().forEach(Output::printNews);
                    break;
                case 2:
                    clientService.registration(Input.inputPerson(Role.CLIENT));
                    break;
                case 0:
                    flag = false;
                    break;
            }
        }
    }
}
