package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.UnlockAccountRequestService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class AccountServiceImplTest extends Base {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private UnlockAccountRequestService accountRequestService;

    @Autowired
    private EntityManager entityManager;

    @Before
    public void init() {
        if (flag) {
            clientService.registration(new PersonDTO(0, "Kirill", "Komarov", 1, 1));
            clientService.registration(new PersonDTO(0, "Vlad", "M", 2, 2));
            clientService.registration(new PersonDTO(0, "Max", "Ul", 3, 3));
            accountService.createAccount(new AccountDTO(true, 0.0), 1);
            accountService.createAccount(new AccountDTO(false, 0.0), 2);
            accountService.createAccount(new AccountDTO(false, 0.0), 1);
            accountRequestService.addAccountRequest(1);
            System.out.println(")))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))");
            flag = false;
        }
    }


    @Test
    public void lockAccount() {
        AccountDTO accountDTO = new AccountDTO(3, true, 0.0);
        assertEquals(accountDTO, accountService.lockAccount(3));
    }

    @Test
    public void getAllAccounts() {
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1, false, 0));
        dtos.add(new AccountDTO(2, false, 0));
        dtos.add(new AccountDTO(3, false, 0));
        assertEquals(dtos, accountService.getAllAccounts());
    }

    @Test
    public void unlockAccount() {
        AccountDTO dto = new AccountDTO(1, false, 0);
        assertEquals(dto, accountService.unlockAccount(1));
    }

    @Test
    public void getAllUnlockAccountRequest() {
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1, true, 0));
        assertEquals(dtos, accountService.getAllUnlockAccountRequest());
    }

    @Test
    public void refill() {
        AccountDTO accountDTO = new AccountDTO(2, false, 100);
        assertEquals(accountDTO, accountService.refill(2));
    }

    @Test
    public void getAccountsByClientIdAndLock() {
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1, true, 0));
        assertEquals(dtos, accountService.getAccountsByClientIdAndLock(1, true));
    }

    @Test
    public void createAccount() {
        AccountDTO accountDTO = new AccountDTO(4, false, 0);
        assertEquals(accountDTO, accountService.createAccount(new AccountDTO(false, 0), 1));
    }
}