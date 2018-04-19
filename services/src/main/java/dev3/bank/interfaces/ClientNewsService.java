package dev3.bank.interfaces;

import dev3.bank.entity.ClientNews;

import java.util.Collection;

public interface ClientNewsService extends BaseService {
    Collection<ClientNews> getAllClientNews();

    Collection<ClientNews> addClientNews(Collection<Long> clientIds, long newsId);
}
