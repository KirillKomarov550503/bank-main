package com.netcracker.komarov.dao.impl;

import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.interfaces.RequestDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class RequestDAOImpl implements RequestDAO {
    private static RequestDAOImpl requestDAO;
    private Connection connection;

    private RequestDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public static synchronized RequestDAOImpl getRequestDAO(Connection connection) {
        if (requestDAO == null) {
            requestDAO = new RequestDAOImpl(connection);
        }
        return requestDAO;
    }

    private Request getRequest(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getLong("id"));
        request.setRequestId(resultSet.getLong("request_id"));
        switch (resultSet.getString("type")) {
            case "ACCOUNT":
                request.setRequestStatus(RequestStatus.ACCOUNT);
                break;
            case "CARD":
                request.setRequestStatus(RequestStatus.CARD);
                break;
        }
        return request;
    }

    @Override
    public Request add(Request entity) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "INSERT INTO Request (request_id, type) VALUES(?, ?) RETURNING id");
        preparedStatement.setLong(1, entity.getRequestId());
        preparedStatement.setString(2, entity.getRequestStatus().toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            entity.setId(resultSet.getLong("id"));
        }
        resultSet.close();
        preparedStatement.close();
        return entity;
    }

    @Override
    public Request getById(long id) throws SQLException {
        Request request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SElECT * FROM request WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }

    @Override
    public Request update(Request entity) throws SQLException {
        Request request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE Request SET request_id = ?, type = ? WHERE id = ?");
        preparedStatement.setLong(1, entity.getRequestId());
        preparedStatement.setString(2, entity.getRequestStatus().toString());
        preparedStatement.setLong(3, entity.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }

    @Override
    public void delete(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "DELETE FROM request WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    @Override
    public Collection<Request> getAll() throws SQLException {
        Collection<Request> requests = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM request");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            requests.add(getRequest(resultSet));
        }
        resultSet.close();
        preparedStatement.close();
        return requests;
    }

    @Override
    public Request findRequestByRequestIdAndStatus(long requestId, RequestStatus requestStatus) throws SQLException {
        Request request = null;
        PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM request WHERE request_id = ? and type = ?");
        preparedStatement.setLong(1, requestId);
        preparedStatement.setString(2, requestStatus.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            request = getRequest(resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return request;
    }
}
