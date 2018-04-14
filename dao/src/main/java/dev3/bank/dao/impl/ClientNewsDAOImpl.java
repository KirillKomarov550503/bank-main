package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.ClientNewsDAO;
import dev3.bank.entity.Client;
import dev3.bank.entity.ClientNews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ClientNewsDAOImpl implements ClientNewsDAO {
    private static ClientNewsDAOImpl clientNewsDAO;
    private Connection connection;

    private ClientNewsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized ClientNewsDAOImpl getClientNewsDAO(Connection connection) {
        if (clientNewsDAO == null) {
            clientNewsDAO = new ClientNewsDAOImpl(connection);
        }
        return clientNewsDAO;
    }

    @Override
    public Collection<ClientNews> getAllByClientId(long clientId) throws SQLException {
        Collection<ClientNews> clientNewsCollection = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ClientNews WHERE client_id=? or client_id=0");
        preparedStatement.setLong(1, clientId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            clientNewsCollection.add(getClientNews(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return clientNewsCollection;
    }

    private ClientNews getClientNews(ResultSet resultSet) throws SQLException {
        ClientNews clientNews = new ClientNews();
        clientNews.setId(resultSet.getLong("id"));
        clientNews.setNewsId(resultSet.getLong("news_id"));
        clientNews.setClientId(resultSet.getLong("client_id"));
        return clientNews;
    }


    @Override
    public ClientNews getById(long id) throws SQLException {
        ClientNews clientNews = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ClientNews WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            clientNews = getClientNews(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return clientNews;
    }

    @Override
    public ClientNews add(ClientNews entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO ClientNews(news_id, client_id) VALUES(?, ?) RETURNING id");
        preparedStatement.setLong(1, entity.getNewsId());
        preparedStatement.setLong(2, entity.getClientId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    @Override
    public ClientNews update(ClientNews entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE ClientNews SET news_id=?, client_id=? WHERE id=?");
        preparedStatement.setLong(1, entity.getNewsId());
        preparedStatement.setLong(2, entity.getClientId());
        preparedStatement.setLong(3, entity.getId());
        preparedStatement.execute();
        preparedStatement.close();
        return entity;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM ClientNews WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Collection<ClientNews> getAll() throws SQLException {
        Collection<ClientNews> newsCollection = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM ClientNews");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            newsCollection.add(getClientNews(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return newsCollection;
    }
}
