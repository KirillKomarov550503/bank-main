package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.ClientNews;

import java.sql.SQLException;
import java.util.Collection;

public interface ClientNewsDAO extends CrudDAO<ClientNews> {
    Collection<ClientNews> getAllByClientId(long clientId) throws SQLException;
}
