package dev3.bank.dao.impl;

import dev3.bank.dao.interfaces.AdminDAO;
import dev3.bank.entity.Admin;

public class AdminDAOImpl extends CrudDAOImpl<Admin> implements AdminDAO {
    public AdminDAOImpl() {
        super(Admin.class);
    }
}
