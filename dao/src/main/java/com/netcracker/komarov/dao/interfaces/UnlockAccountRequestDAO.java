package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.UnlockAccountRequest;

import java.sql.SQLException;

public interface UnlockAccountRequestDAO extends CrudDAO<UnlockAccountRequest> {
    UnlockAccountRequest getByAccountId(long accountId) throws SQLException;
}
