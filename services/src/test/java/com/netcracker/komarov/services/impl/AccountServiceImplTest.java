package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.TestConfig;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountServiceImplTest {
    @Mock
    @Autowired
    private AccountService accountService;

    @Autowired
    private PersonService personService;

    @Before
    public void init() {
        personService.save(new PersonDTO(0L, "Kirill", "Komarov", "1",
                "1", "Optimist", "qwerty", Role.CLIENT));
        personService.save(new PersonDTO(0L, "Vlad", "M", "1",
                "1", "Realist", "12345", Role.CLIENT));
        personService.save(new PersonDTO(0L, "Max", "Ul",
                "3", "3", "Pessimist", "password", Role.CLIENT));
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
        accountService.saveAccount(new AccountDTO(false, 0.0), 2);
        accountService.saveAccount(new AccountDTO(false, 0.0), 1);
    }

    @Test
    public void lockAccount() {
        AccountDTO accountDTO = new AccountDTO(3L, true, 0.0, 1L);
        assertEquals(accountDTO, accountService.lockAccount(3));
    }

    @Test
    public void findAllAccounts() {
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1L, false, 0.0, 1L));
        dtos.add(new AccountDTO(2L, false, 0.0, 2L));
        dtos.add(new AccountDTO(3L, false, 0.0, 1L));
        assertEquals(dtos, accountService.findAllAdmins());
    }

    @Test
    public void unlockAccount() {
        accountService = mock(AccountService.class);
        AccountDTO dto = new AccountDTO(1L, false, 0.0, 1L);
        doReturn(dto).when(accountService).unlockAccount(1);
        accountService.unlockAccount(1);
        verify(accountService, times(1)).unlockAccount(1);
    }

    @Test
    public void refillAccount() {
        AccountDTO accountDTO = new AccountDTO(2L, false, 100.0, 2L);
        assertEquals(accountDTO, accountService.refillAccount(2));
    }

    @Test
    public void findAccountsByClientIdAndLock() {
        accountService.lockAccount(1);
        accountService.lockAccount(3);
        Collection<AccountDTO> dtos = new ArrayList<>();
        dtos.add(new AccountDTO(1L, true, 0.0, 1L));
        dtos.add(new AccountDTO(3L, true, 0.0, 1L));
        assertEquals(dtos, accountService.findAccountsByClientIdAndLock(1, true));
    }

    @Test
    public void createAccount() {
        personService.save(new PersonDTO(0L, "Kirill", "Komarov",
                "1", "1", "optimist", "qwerty"));
        AccountDTO accountDTO = new AccountDTO(4L, false, 0.0, 1L);
        assertEquals(accountDTO, accountService.saveAccount(new AccountDTO(false, 0.0), 1));
    }

    @Test
    public void deleteById() {
        accountService = mock(AccountService.class);
        doNothing().when(accountService).deleteById(1);
        accountService.deleteById(1);
        verify(accountService, times(1)).deleteById(1);
    }

    @Test
    public void findById() {
        AccountDTO accountDTO = new AccountDTO(3L, false, 0.0, 1L);
        assertEquals(accountDTO, accountService.findById(3));
    }
}