package dev3.bank.dao.interfaces;

import dev3.bank.entity.ClientNews;

import java.util.Collection;

public interface ClientNewsDAO extends CrudDAO<ClientNews> {
    Collection<ClientNews> getAllByClientId(long clientId);
    Collection<ClientNews> getAllUnviewedByClientId(long clientId);
}
