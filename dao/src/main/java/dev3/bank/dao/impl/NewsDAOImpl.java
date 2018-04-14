package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.NewsDAO;
import dev3.bank.entity.News;
import dev3.bank.entity.NewsStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class NewsDAOImpl implements NewsDAO {
    private static NewsDAOImpl newsDAO;
    private Connection connection;

    private NewsDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized NewsDAOImpl getNewsDAO(Connection connection) {
        if (newsDAO == null) {
            newsDAO = new NewsDAOImpl(connection);
        }
        return newsDAO;
    }

    @Override
    public Collection<News> getNewsByAdmin(long adminId) throws SQLException {
        Collection<News> newsCollection = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM News WHERE admin_id=?");
        preparedStatement.setLong(1, adminId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            newsCollection.add(getNews(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return newsCollection;
    }

    @Override
    public Collection<News> getNewsByStatus(NewsStatus newsStatus) throws SQLException {
        Collection<News> newsCollection = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM News WHERE news_status=?");
        preparedStatement.setString(1, newsStatus.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            newsCollection.add(getNews(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return newsCollection;
    }

    private News getNews(ResultSet resultSet) throws SQLException {
        News news = new News();
        news.setId(resultSet.getLong("id"));
        setSwitch(news, resultSet);
        news.setDate(resultSet.getString("date"));
        news.setAdminId(resultSet.getLong("admin_id"));
        news.setTitle(resultSet.getString("title"));
        news.setText(resultSet.getString("body"));
        return news;
    }

    @Override
    public News getById(long id) throws SQLException {
        News news = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM News WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            news = getNews(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return news;
    }

    private void setSwitch(News news, ResultSet resultSet) throws SQLException {
        switch (resultSet.getString("news_status")) {
            case "GENERAL":
                news.setNewsStatus(NewsStatus.GENERAL);
                break;
            case "CLIENT":
                news.setNewsStatus(NewsStatus.CLIENT);
                break;
        }
    }


    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM News WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public News add(News entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO News(admin_id, date, title, body, news_status) VALUES(?, ?, ?, ?, ?)" +
                "RETURNING id");
        preparedStatement.setLong(1, entity.getAdminId());
        preparedStatement.setString(2, entity.getDate());
        preparedStatement.setString(3, entity.getTitle());
        preparedStatement.setString(4, entity.getText());
        preparedStatement.setString(5, entity.getNewsStatus().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    @Override
    public News update(News entity) throws SQLException {
        News news = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE News SET admin_id=?, date=?, title=?, body=?, news_status=? WHERE id=?");
        preparedStatement.setLong(1, entity.getAdminId());
        preparedStatement.setString(2, entity.getDate());
        preparedStatement.setString(3, entity.getTitle());
        preparedStatement.setString(4, entity.getText());
        preparedStatement.setString(5, entity.getNewsStatus().toString());
        preparedStatement.setLong(6, entity.getId());
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM News WHERE id=?");
        preparedStatement.setLong(1, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            news = getNews(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return news;
    }

    @Override
    public Collection<News> getAll() throws SQLException {
        Collection<News> newsCollection = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM News");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            newsCollection.add(getNews(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return newsCollection;
    }
}
