package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import com.netcracker.komarov.dao.interfaces.UnlockCardRequestDAO;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.UnlockCardRequestService;
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
