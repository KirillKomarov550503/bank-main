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
    public Collection<News> getNewsByAdmin(long adminId) {
        Collection<News> newsCollection = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM News WHERE admin_id=?");
            preparedStatement.setLong(1, adminId);
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(newsCollection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }

    @Override
    public Collection<News> getNewsByStatus(NewsStatus newsStatus) {
        Collection<News> newsCollection = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM News WHERE news_status=?");
            preparedStatement.setString(1, newsStatus.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(newsCollection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }

    private void parseNewsFromResultSet(Collection<News> newsCollection, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            News news = new News();
            news.setId(resultSet.getLong("id"));
            setSwitch(news, resultSet);
            news.setDate(resultSet.getString("date"));
            news.setAdminId(resultSet.getLong("admin_id"));
            news.setTitle(resultSet.getString("title"));
            news.setText(resultSet.getString("body"));
            newsCollection.add(news);
        }
    }

    @Override
    public News getById(long id) {
        News news = new News();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "SELECT * FROM News WHERE id=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(news, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void parseNewsFromResultSet(News news, ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            news.setId(resultSet.getLong("id"));
            setSwitch(news, resultSet);
            news.setDate(resultSet.getString("date"));
            news.setAdminId(resultSet.getLong("admin_id"));
            news.setTitle(resultSet.getString("title"));
            news.setText(resultSet.getString("body"));
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "DELETE FROM News WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public News add(News entity) {
        News news = new News();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO News(admin_id, date, title, body, news_status) VALUES(?, ?, ?, ?, ?)");
            preparedStatement.setLong(1, entity.getAdminId());
            preparedStatement.setString(2, entity.getDate());
            preparedStatement.setString(3, entity.getTitle());
            preparedStatement.setString(4, entity.getText());
            preparedStatement.setString(5, entity.getNewsStatus().toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(news, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }

    @Override
    public News update(News entity) {
        News news = new News();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE News SET admin_id=?, date=?, title=?, body=?, news_status=? WHERE id=?");
            preparedStatement.setLong(1, entity.getAdminId());
            preparedStatement.setString(2, entity.getDate());
            preparedStatement.setString(3, entity.getTitle());
            preparedStatement.setString(4, entity.getText());
            preparedStatement.setString(5, entity.getNewsStatus().toString());
            preparedStatement.setLong(6, entity.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(news, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return news;
    }
}
