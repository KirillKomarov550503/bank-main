package dev3.bank.impl;

import dev3.bank.dao.interfaces.AccountDAO;
import dev3.bank.dao.interfaces.UnlockAccountRequestDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Account;
import dev3.bank.entity.UnlockAccountRequest;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountDAO accountDAO;
    private UnlockAccountRequestDAO unlockAccountRequestDAO;

    @Override
    public void setDAO(DAOFactory daoFactory) {
        accountDAO = daoFactory.getAccountDAO();
        unlockAccountRequestDAO = daoFactory.getUnlockAccountRequestDAO();
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
    public void unlockAccount(long accountId) {
        UnlockAccountRequest request = null;
        Account account = null;
        try {
            request = unlockAccountRequestDAO.getAll()
                    .stream()
                    .filter(unlockAccountRequest -> unlockAccountRequest.getAccountId() == accountId)
                    .findFirst()
                    .orElse(null);
            account = accountDAO.getById(request.getAccountId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        try {
            account.setLocked(false);
            connection.setAutoCommit(false);
            accountDAO.update(account);
            unlockAccountRequestDAO.delete(request.getId());
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
    }

    @Override
    public Collection<Account> getAllUnlockAccountRequest() {
        Collection<Account> accounts = new ArrayList<>();
        try {
            Collection<UnlockAccountRequest> requests = unlockAccountRequestDAO.getAll();
            for (UnlockAccountRequest request : requests) {
                accounts.add(accountDAO.getById(request.getAccountId()));
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
