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
import java.util.stream.Collectors;

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
            switch (resultSet.getString("news_status")) {
                case "GENERAL":
                    news.setNewsStatus(NewsStatus.GENERAL);
                    break;
                case "CLIENT":
                    news.setNewsStatus(NewsStatus.CLIENT);
                    break;
            }
            news.setDate(resultSet.getString("date"));
            news.setAdminId(resultSet.getLong("admin_id"));
            news.setTitle(resultSet.getString("title"));
            news.setText(resultSet.getString("body"));
            newsCollection.add(news);
        }
    }

    @Override
    public Collection<News> getNewsByDate(String date) {
        Collection<News> newsCollection = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "");
            preparedStatement.setString(1, newsStatus.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            parseNewsFromResultSet(newsCollection, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newsCollection;
    }
}
