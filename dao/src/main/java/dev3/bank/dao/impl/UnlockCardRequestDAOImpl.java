package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.UnlockCardRequestDAO;
import dev3.bank.entity.UnlockCardRequest;

public class UnlockCardRequestDAOImpl extends CrudDAOImpl<UnlockCardRequest> implements UnlockCardRequestDAO {
    public UnlockCardRequestDAOImpl() {
        super(UnlockCardRequest.class);
    }

    @Override
    public UnlockCardRequest getByCardId(long cardId) {
        return getEntityMapValues()
                .stream()
                .filter(unlockCardRequest -> unlockCardRequest.getCard().getId() == cardId)
                .findFirst()
                .orElse(null);
    }
}
