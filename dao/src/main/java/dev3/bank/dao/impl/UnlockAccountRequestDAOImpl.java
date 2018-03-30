package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.UnlockAccountRequestDAO;
import dev3.bank.entity.UnlockAccountRequest;

public class UnlockAccountRequestDAOImpl extends CrudDAOImpl<UnlockAccountRequest> implements UnlockAccountRequestDAO {
    public UnlockAccountRequestDAOImpl(){
        super(UnlockAccountRequest.class);
    }
}
