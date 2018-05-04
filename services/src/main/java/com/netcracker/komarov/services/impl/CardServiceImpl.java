package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Card;
import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.interfaces.CardDAO;
import com.netcracker.komarov.dao.interfaces.RequestDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CardServiceImpl implements CardService {
    private RequestDAO requestDAO;
    private CardDAO cardDAO;

    @Autowired
    public CardServiceImpl(DAOFactory daoFactory) {
        this.requestDAO = daoFactory.getRequestDAO();
        this.cardDAO = daoFactory.getCardDAO();
    }

    @Override
    public Card lockCard(long cardId) {
        Card temp = null;
        try {
            Card card = cardDAO.getById(cardId);
            card.setLocked(true);
            temp = cardDAO.update(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Card createCard(Card card, long accountId) {
        card.setAccountId(accountId);
        Card temp = null;
        try {
            temp = cardDAO.add(card);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }


    @Override
    public Collection<Card> getLockCards(long clientId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getLockedCardsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }


    @Override
    public Collection<Card> getUnlockCards(long clientId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getUnlockedCardsByClientId(clientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getAllCardsByAccount(long accountId) {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getCardsByAccountId(accountId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getAllUnlockCardRequest() {
        Collection<Card> cards = new ArrayList<>();
        try {
            Collection<Request> requests = requestDAO.getAll();
            for (Request request : requests) {
                if (request.getRequestStatus().equals(RequestStatus.CARD)) {
                    cards.add(cardDAO.getById(request.getRequestId()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Card unlockCard(long cardId) {
        Request request = null;
        Card card = null;
        try {
            request = requestDAO.getAll()
                    .stream()
                    .filter(elem -> elem.getRequestStatus().equals(RequestStatus.CARD))
                    .filter(elem -> elem.getRequestId() == cardId)
                    .findFirst()
                    .orElse(null);
            card = cardDAO.getById(cardId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        Card temp = null;
        try {
            connection.setAutoCommit(false);
            card.setLocked(false);
            temp = cardDAO.update(card);
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
    public Collection<Card> getAllCards() {
        Collection<Card> cards = null;
        try {
            cards = cardDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
