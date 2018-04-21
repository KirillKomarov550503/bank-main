package dev3.bank.impl;

import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.dao.interfaces.UnlockCardRequestDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Card;
import dev3.bank.entity.UnlockCardRequest;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class CardServiceImpl implements CardService {
    private UnlockCardRequestDAO unlockCardRequestDAO;
    private CardDAO cardDAO;

    @Autowired
    public CardServiceImpl(DAOFactory daoFactory) {
        this.unlockCardRequestDAO = daoFactory.getUnlockCardRequestDAO();
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
            Collection<UnlockCardRequest> requests = unlockCardRequestDAO.getAll();
            for (UnlockCardRequest request : requests) {
                cards.add(cardDAO.getById(request.getCardId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void unlockCard(long cardId) {
        UnlockCardRequest request = null;
        Card card = null;
        try {
            request = unlockCardRequestDAO.getAll()
                    .stream()
                    .filter(unlockCardRequest -> unlockCardRequest.getCardId() == cardId)
                    .findFirst()
                    .orElse(null);
            card = cardDAO.getById(request.getCardId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Connection connection = DataBase.getConnection();
        try {
            connection.setAutoCommit(false);
            card.setLocked(false);
            cardDAO.update(card);
            unlockCardRequestDAO.delete(request.getId());
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
