package dev3.bank.dao.interfaces;

import dev3.bank.entity.ClientNews;

import java.sql.SQLException;
import java.util.Collection;

public interface ClientNewsDAO extends CrudDAO<ClientNews> {
    Collection<ClientNews> getAllByClientId(long clientId) throws SQLException;
}
