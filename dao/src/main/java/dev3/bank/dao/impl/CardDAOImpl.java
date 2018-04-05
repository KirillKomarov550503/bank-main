package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.CardDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class CardDAOImpl extends CrudDAOImpl<Card> implements CardDAO {
    private static CardDAOImpl cardDAO;

    private CardDAOImpl() {
        try (Connection connection = DataBase.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS Card(" +
                    "id BIGSERIAL NOT NULL PRIMARY KEY," +
                    "locked BOOLEAN NOT NULL," +
                    "pin INT NOT NULL," +
                    "card_id BIGINT NOT NULL," +
                    "account_id BIGINT NOT NULL REFERENCES Account(id))");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<Card> getLockedCards() {
        Collection<Card> cards = new ArrayList<>();
        try (Connection connection = DataBase.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE locked=TRUE");
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(cards, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Collection<Card> getCardsByClientId(long clientId) {
        Collection<Card> cards = new ArrayList<>();
        try(Connection connection = DataBase.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM Card WHERE account_id IN (SELECT id FROM Account WHERE client_id=?)");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(cards, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private void parseResultSet(Collection<Card> cards, ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            Card card = new Card();
            card.setId(resultSet.getLong("id"));
            card.setLocked(resultSet.getBoolean("locked"));
            card.setPin(resultSet.getInt("pin"));
            card.setCardId(resultSet.getLong("card_id"));
            card.setAccountId(resultSet.getLong("account_id"));
            cards.add(card);
        }
    }

    @Override
    public Collection<Card> getCardsByAccountId(long accountId) {
        Collection<Card> cards = new ArrayList<>();
        try (Connection connection = DataBase.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM card WHERE account_id=?");
            ResultSet resultSet = preparedStatement.executeQuery();
            parseResultSet(cards, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEntityMapValues()
                .stream()
                .filter(card -> card.getAccount().getId() == accountId)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Card> getUnlockedCards() {
        return getEntityMapValues()
                .stream()
                .filter(card -> !card.isLocked())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Card> getLockedCardsByClientId(long clientId) {
        return getEntityMapValues()
                .stream()
                .filter(card -> card.isLocked() && (card.getAccount().getClient().getId() == clientId))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Card> getUnlockedCardsByClientId(long clientId) {
        return getEntityMapValues()
                .stream()
                .filter(card -> (!card.isLocked()) && (card.getAccount().getClient().getId() == clientId))
                .collect(Collectors.toList());
    }
}
