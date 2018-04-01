package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientNewsDAO;
import dev3.bank.entity.ClientNews;

import java.util.Collection;
import java.util.stream.Collectors;

public class ClientNewsDAOImpl extends CrudDAOImpl<ClientNews> implements ClientNewsDAO {
    public ClientNewsDAOImpl() {
        super(ClientNews.class);
    }

    @Override
    public Collection<ClientNews> getAllByClientId(long clientId) {
        return getEntityMapValues()
                .stream()
                .filter(clientNews -> (clientNews.getClient().getId() == clientId)
                        || (clientNews.getClient().getId() == 0))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ClientNews> getAllUnviewedByClientId(long clientId) {
        return getEntityMapValues()
                .stream()
                .filter(clientNews -> ((clientNews.getClient().getId() == clientId)
                        || (clientNews.getClient().getId() == 0)) && !clientNews.isViewed())
                .collect(Collectors.toList());
    }
}
