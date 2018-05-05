package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;
import com.netcracker.komarov.dao.interfaces.RequestDAO;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestDAO requestDAO;

    @Autowired
    public RequestServiceImpl(DAOFactory daoFactory) {
        this.requestDAO = daoFactory.getRequestDAO();
    }

    @Override
    public Request saveRequest(long requestId, RequestStatus requestStatus) {
        Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestStatus(requestStatus);
        Request temp = null;
        try {
            temp = requestDAO.add(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }

    @Override
    public Collection<Request> findAllRequests() {
        Collection<Request> requests = null;
        try {
            requests = requestDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public Request findById(long id) {
        Request request = null;
        try {
            request = requestDAO.getById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return request;
    }

    @Override
    public void delete(long id) {
        try {
            requestDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
