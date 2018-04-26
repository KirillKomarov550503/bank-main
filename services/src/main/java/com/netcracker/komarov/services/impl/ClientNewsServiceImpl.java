package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.ClientNews;
import com.netcracker.komarov.dao.interfaces.ClientNewsDAO;
import com.netcracker.komarov.dao.utils.DataBase;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.ClientNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class ClientNewsServiceImpl implements ClientNewsService {
    private ClientNewsDAO clientNewsDAO;

    @Autowired
    public ClientNewsServiceImpl(DAOFactory daoFactory) {
        this.clientNewsDAO = daoFactory.getClientNewsDAO();
    }

    @Override
    public Collection<ClientNews> getAllClientNews() {
        Collection<ClientNews> temp = null;
        try {
            temp = clientNewsDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<ClientNews> addClientNews(Collection<Long> clientIds, long newsId) {
        Connection connection = DataBase.getConnection();
        Collection<ClientNews> newsCollection = new ArrayList<>();
        try {
            connection.setAutoCommit(false);
            for (Long clientId : clientIds) {
                ClientNews clientNews = new ClientNews();
                clientNews.setClientId(clientId);
                clientNews.setNewsId(newsId);
                clientNewsDAO.add(clientNews);
                newsCollection.add(clientNews);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("SQL exception");
            }
        }
        return newsCollection;
    }
}
