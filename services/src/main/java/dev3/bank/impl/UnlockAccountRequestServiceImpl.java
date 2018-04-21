package dev3.bank.impl;

import dev3.bank.dao.interfaces.UnlockAccountRequestDAO;
import dev3.bank.entity.UnlockAccountRequest;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.UnlockAccountRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class UnlockAccountRequestServiceImpl implements UnlockAccountRequestService {
    private UnlockAccountRequestDAO unlockAccountRequestDAO;

    @Autowired
    public UnlockAccountRequestServiceImpl(DAOFactory daoFactory) {
        this.unlockAccountRequestDAO = daoFactory.getUnlockAccountRequestDAO();
    }

    @Override
    public Collection<UnlockAccountRequest> getAllAccountRequest() {
        Collection<UnlockAccountRequest> temp = null;
        try {
            temp = unlockAccountRequestDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public UnlockAccountRequest unlockAccountRequest(long accountId) {
        UnlockAccountRequest temp = null;
        try {
            if (unlockAccountRequestDAO.getByAccountId(accountId) == null) {
                UnlockAccountRequest request = new UnlockAccountRequest();
                request.setAccountId(accountId);
                temp = unlockAccountRequestDAO.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
