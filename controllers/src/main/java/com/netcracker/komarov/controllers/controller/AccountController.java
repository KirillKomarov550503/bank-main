package com.netcracker.komarov.controllers.controller;

import com.netcracker.komarov.controllers.exception.ServerException;
import com.netcracker.komarov.services.dto.entity.AccountDTO;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/v1")
public class AccountController implements ExceptionController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/clients/{clientId}/accounts", method = RequestMethod.POST)
    public AccountDTO create(@PathVariable long clientId) {
        AccountDTO dto = accountService.createAccount(new AccountDTO(false, 0), clientId);
        if (dto == null) {
            throw new ServerException("Server can't create new account");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/requests/accounts/{accountId}/unbloking", method = RequestMethod.GET)
    public AccountDTO unlock(@PathVariable long adminId, @PathVariable long accountId) {
        AccountDTO dto = accountService.unlockAccount(accountId);
        if (dto == null) {
            throw new NullPointerException("account");
        } else if (dto.isLocked()) {
            throw new ServerException("Failed to unlock account");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/blocking", method = RequestMethod.GET)
    public AccountDTO lock(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.lockAccount(accountId);
        if (dto == null) {
            throw new NullPointerException("account");
        } else if (!dto.isLocked()) {
            throw new ServerException("Failed to lock account");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/accounts/{accountId}/money", method = RequestMethod.GET)
    public AccountDTO refill(@PathVariable long clientId, @PathVariable long accountId) {
        AccountDTO dto = accountService.refill(accountId);
        if (dto == null) {
            throw new NullPointerException("account");
        }
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/clients/{clientId}/accounts/status", method = RequestMethod.GET)
    public Collection<AccountDTO> getByClientIdAndLock(@PathVariable long clientId, @RequestParam(name = "lock",
            required = false, defaultValue = "false") boolean lock) {
        Collection<AccountDTO> dtos = accountService.getAccountsByClientIdAndLock(clientId, lock);
        if (dtos == null) {
            throw new NullPointerException("client");
        }
        return dtos;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/accounts", method = RequestMethod.GET)
    public Collection<AccountDTO> getAll(@PathVariable long adminId) {
        return accountService.getAllAccounts();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/admins/{adminId}/requests/accounts", method = RequestMethod.GET)
    public Collection<AccountDTO> getAllRequests(@PathVariable long adminId) {
        return accountService.getAllUnlockAccountRequest();
    }
}
