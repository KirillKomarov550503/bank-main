package dev3.bank.interfaces;

import dev3.bank.factory.DAOFactory;

public interface BaseService {
    void setDAO(DAOFactory daoFactory);
}
