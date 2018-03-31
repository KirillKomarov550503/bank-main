package dev3.bank.dao.interfaces;

import dev3.bank.entity.UnlockCardRequest;

public interface UnlockCardRequestDAO extends CrudDAO<UnlockCardRequest> {
    UnlockCardRequest getByCardId(long cardId);
}
