package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Account;
import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.interfaces.AccountDAO;
import com.netcracker.komarov.dao.interfaces.RequestDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountDAO accountDAO;
    private RequestDAO requestDAO;

    @Autowired
    public AccountServiceImpl(DAOFactory daoFactory) {
        this.accountDAO = daoFactory.getAccountDAO();
        this.requestDAO = daoFactory.getRequestDAO();
    }

    @Override
    public Account lockAccount(long accountId) {
        Account temp = null;
        try {
            Account account = accountDAO.getById(accountId);
            account.setLocked(true);
            temp = accountDAO.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<Account> getAllAccounts() {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account unlockAccount(long accountId) {
        Request request = null;
        Account account = null;
        try {
            request = requestDAO.getAll()
                    .stream()
                    .filter(elem -> elem.getRequestStatus().equals(RequestStatus.ACCOUNT))
                    .filter(elem -> elem.getRequestId() == accountId)
                    .findFirst()
                    .orElse(null);
            account = accountDAO.getById(accountId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        Account temp = null;
        try {
            account.setLocked(false);
            connection.setAutoCommit(false);
            temp = accountDAO.update(account);
            requestDAO.delete(request.getId());
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                connection.rollback();
                connection.setAutoCommit(true);
            } catch (SQLException e1) {
                System.out.println("SQL exception");
            }
            System.out.println("SQL exception");
        }
        return temp;
    }

    @Override
    public Collection<Account> getAllAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        try {
            Collection<Request> requests = requestDAO.getAll();
            for (Request request : requests) {
                if (request.getRequestStatus().equals(RequestStatus.ACCOUNT)) {
                    accounts.add(accountDAO.getById(request.getId()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account refill(long accountId) {
        Account temp = null;
        try {
            Account account = accountDAO.getById(accountId);
            double balance = account.getBalance();
            account.setBalance(balance + 100.0);
            temp = accountDAO.update(account);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<Account> getLockAccounts(long clientId) {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getLockedAccountsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Collection<Account> getUnlockAccounts(long clientId) {
        Collection<Account> accounts = null;
        try {
            accounts = accountDAO.getUnlockedAccountsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account createAccount(Account account, long clientId) {
        account.setClientId(clientId);
        Account temp = null;
        try {
            temp = accountDAO.add(account);
        } catch (SQLException e) {
            System.out.println("SQL exception");
        }
        return temp;
    }
}
