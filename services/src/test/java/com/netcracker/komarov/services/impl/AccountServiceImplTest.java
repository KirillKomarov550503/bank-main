package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.RequestStatus;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class AccountServiceImplTest {

    @Mock
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private RequestService requestService;

    @Before
    public void init() {
        clientService.save(new PersonDTO(0, "Kirill", "Komarov", 1, 1));
        clientService.save(new PersonDTO(0, "Vlad", "M", 2, 2));
        clientService.save(new PersonDTO(0, "Max", "Ul", 3, 3));
        accountService.createAccount(new AccountDTO(false, 0.0), 1);
        accountService.createAccount(new AccountDTO(false, 0.0), 2);
        accountService.createAccount(new AccountDTO(false, 0.0), 1);
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
        requestService.saveRequest(1, RequestStatus.ACCOUNT);
        AccountDTO dto = new AccountDTO(1, false, 0);
        assertEquals(dto, accountService.unlockAccount(1));
    }

    @Test
    public void refill() {
        AccountDTO accountDTO = new AccountDTO(2, false, 100);
        assertEquals(accountDTO, accountService.refill(2));
    }

    @Test
    public void getAccountsByClientIdAndLock() {
        accountService.lockAccount(1);
        accountService.lockAccount(3);
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1, true, 0));
        dtos.add(new AccountDTO(3, true, 0));
        assertEquals(dtos, accountService.getAccountsByClientIdAndLock(1, true));
    }

    @Test
    public void createAccount() {
        clientService.save(new PersonDTO(0, "Kirill", "Komarov", 1, 1));
        AccountDTO accountDTO = new AccountDTO(4, false, 0);
        assertEquals(accountDTO, accountService.createAccount(new AccountDTO(false, 0), 1));
    }

    @Test
    public void deleteById() {
        accountService = mock(AccountService.class);
        doNothing().when(accountService).deleteById(1);
        accountService.deleteById(1);
        verify(accountService, times(1)).deleteById(1);
    }
}