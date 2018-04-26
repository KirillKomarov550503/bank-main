package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.ClientNews;

import java.util.Collection;

public interface ClientNewsService {
    Collection<ClientNews> getAllClientNews();

    Collection<ClientNews> addClientNews(Collection<Long> clientIds, long newsId);
}
