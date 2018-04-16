package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CardDAOImpl implements CardDAO {
    private static CardDAOImpl cardDAO;
    private Connection connection;

    private CardDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized CardDAOImpl getCardDAO(Connection connection) {
        if (cardDAO == null) {
            cardDAO = new CardDAOImpl(connection);
        }
        return cardDAO;
    }

    @Override
    public Card getById(long id) throws SQLException {
        Card card = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            card = getCard(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return card;
    }

    @Override
    public Card update(Card entity) throws SQLException {
        Card card = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Card SET locked=?, pin=?, card_id=?, account_id=? WHERE id=?");
        preparedStatement.setBoolean(1, entity.isLocked());
        preparedStatement.setInt(2, entity.getPin());
        preparedStatement.setLong(3, entity.getCardId());
        preparedStatement.setLong(4, entity.getAccountId());
        preparedStatement.setLong(5, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            card = getCard(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return card;
    }

    private Card getCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong("id"));
        card.setLocked(resultSet.getBoolean("locked"));
        card.setCardId(resultSet.getLong("card_id"));
        card.setPin(resultSet.getInt("pin"));
        card.setAccountId(resultSet.getLong("account_id"));
        return card;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM Card WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Card add(Card entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO Card (locked, pin, card_id, account_id) VALUES(?, ?, ?, ?)" +
                "RETURNING id");
        preparedStatement.setBoolean(1, entity.isLocked());
        preparedStatement.setInt(2, entity.getPin());
        preparedStatement.setLong(3, entity.getCardId());
        preparedStatement.setLong(4, entity.getAccountId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }


    @Override
    public Collection<Card> getAll() throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }

    @Override
    public Card getByCardId(long cardId) throws SQLException {
        Card card = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE card_id=?");
        preparedStatement.setLong(1, cardId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            card = getCard(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return card;
    }

    @Override
    public Collection<Card> getLockedCards() throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE locked=TRUE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }

    @Override
    public Collection<Card> getCardsByClientId(long clientId) throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE account_id IN (SELECT id FROM Account WHERE client_id=?)");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }

    @Override
    public Collection<Card> getCardsByAccountId(long accountId) throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE account_id=?");
        preparedStatement.setLong(1, accountId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }

    @Override
    public Collection<Card> getUnlockedCards() throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE locked=FALSE");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }

    @Override
    public Collection<Card> getLockedCardsByClientId(long clientId) throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE account_id IN (SELECT id FROM account WHERE id=?)" +
                " and locked=TRUE");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }


    @Override
    public Collection<Card> getUnlockedCardsByClientId(long clientId) throws SQLException {
        Collection<Card> cards = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM Card WHERE account_id IN (SELECT id FROM account WHERE client_id =?) " +
                "AND locked=FALSE");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            cards.add(getCard(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return cards;
    }
}
