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
    public Card getById(long id) {
        Card card = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = getCard(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public Card update(Card entity) {
        Card card = null;
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM Card WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Card add(Card entity) {
        Card card = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO Card (locked, pin, card_id, account_id) VALUES(?, ?, ?, ?)");
            preparedStatement.setBoolean(1, entity.isLocked());
            preparedStatement.setInt(2, entity.getPin());
            preparedStatement.setLong(3, entity.getCardId());
            preparedStatement.setLong(4, entity.getAccountId());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE card_id=?");
            preparedStatement.setLong(1, entity.getCardId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = getCard(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }


    @Override
    public Collection<Card> getAll() {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Card getByCardId(long cardId) {
        Card card = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE card_id=?");
            preparedStatement.setLong(1, cardId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card = getCard(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public Collection<Card> getLockedCards() {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE locked=TRUE");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getCardsByClientId(long clientId) {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE account_id IN (SELECT id FROM Account WHERE client_id=?)");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getCardsByAccountId(long accountId) {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE account_id=?");
            preparedStatement.setLong(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getUnlockedCards() {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE locked=FALSE");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getLockedCardsByClientId(long clientId) {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE id=? and locked=TRUE");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }


    @Override
    public Collection<Card> getUnlockedCardsByClientId(long clientId) {
        Collection<Card> cards = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE id=? and locked=FALSE");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cards.add(getCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }
}
