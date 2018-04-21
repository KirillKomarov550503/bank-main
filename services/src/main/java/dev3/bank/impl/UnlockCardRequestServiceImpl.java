package dev3.bank.impl;

import dev3.bank.dao.interfaces.UnlockCardRequestDAO;
import dev3.bank.entity.UnlockCardRequest;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.UnlockCardRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class UnlockCardRequestServiceImpl implements UnlockCardRequestService {
    private UnlockCardRequestDAO unlockCardRequestDAO;

    @Autowired
    public UnlockCardRequestServiceImpl(DAOFactory daoFactory) {
        this.unlockCardRequestDAO = daoFactory.getUnlockCardRequestDAO();
    }

    @Override
    public Collection<UnlockCardRequest> getAllCardRequest() {
        Collection<UnlockCardRequest> temp = null;
        try {
            temp = unlockCardRequestDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public UnlockCardRequest unlockCardRequest(long cardId) {
        UnlockCardRequest temp = null;
        try {
            if (unlockCardRequestDAO.getByCardId(cardId) == null) {
                UnlockCardRequest request = new UnlockCardRequest();
                request.setCardId(cardId);
                temp = unlockCardRequestDAO.add(request);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
