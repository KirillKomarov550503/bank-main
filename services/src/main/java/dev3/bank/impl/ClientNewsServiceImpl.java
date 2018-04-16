package dev3.bank.impl;

import dev3.bank.dao.interfaces.ClientNewsDAO;
import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.ClientNews;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.ClientNewsService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClientNewsServiceImpl implements ClientNewsService {
    private ClientNewsDAO clientNewsDAO;
    private static ClientNewsServiceImpl clientNewsService;

    private ClientNewsServiceImpl() {
    }

    public static synchronized ClientNewsServiceImpl getClientNewsService() {
        if (clientNewsService == null) {
            clientNewsService = new ClientNewsServiceImpl();
        }
        return clientNewsService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        clientNewsDAO = daoFactory.getClientNewsDAO();
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
