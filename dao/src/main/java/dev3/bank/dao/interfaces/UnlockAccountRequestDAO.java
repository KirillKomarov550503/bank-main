package dev3.bank.dao.interfaces;

import dev3.bank.entity.UnlockAccountRequest;

import java.sql.SQLException;

public interface UnlockAccountRequestDAO extends CrudDAO<UnlockAccountRequest> {
    UnlockAccountRequest getByAccountId(long accountId) throws SQLException;
}
