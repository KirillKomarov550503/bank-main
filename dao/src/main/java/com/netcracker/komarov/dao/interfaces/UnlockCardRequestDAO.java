package com.netcracker.komarov.dao.interfaces;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;

import java.sql.SQLException;

public interface UnlockCardRequestDAO extends CrudDAO<UnlockCardRequest> {
    UnlockCardRequest getByCardId(long cardId) throws SQLException;
}
