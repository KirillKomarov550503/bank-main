package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.Request;
import com.netcracker.komarov.dao.entity.RequestStatus;

import java.sql.SQLException;

public interface RequestDAO extends CrudDAO<Request> {
    Request findRequestByRequestIdAndStatus(long requestId, RequestStatus requestStatus) throws SQLException;
}
