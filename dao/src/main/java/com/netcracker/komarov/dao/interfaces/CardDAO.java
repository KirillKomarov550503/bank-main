package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.Card;

import java.sql.SQLException;
import java.util.Collection;

public interface CardDAO extends CrudDAO<Card> {
    Collection<Card> getLockedCards() throws SQLException;
    Collection<Card> getUnlockedCards() throws SQLException;
    Collection<Card> getCardsByAccountId(long accountId) throws SQLException;
    Collection<Card> getLockedCardsByClientId(long clientId) throws SQLException;
    Collection<Card> getUnlockedCardsByClientId(long clientId) throws SQLException;
    Collection<Card> getCardsByClientId(long clientId) throws SQLException;
}
