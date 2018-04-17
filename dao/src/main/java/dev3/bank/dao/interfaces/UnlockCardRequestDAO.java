package dev3.bank.dao.interfaces;

import dev3.bank.entity.UnlockCardRequest;

import java.sql.SQLException;

public interface UnlockCardRequestDAO extends CrudDAO<UnlockCardRequest> {
    UnlockCardRequest getByCardId(long cardId) throws SQLException;
}
