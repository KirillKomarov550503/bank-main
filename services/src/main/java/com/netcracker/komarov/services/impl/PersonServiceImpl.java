package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.interfaces.PersonDAO;
import com.netcracker.komarov.services.factory.DAOFactory;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(DAOFactory daoFactory) {
        this.personDAO = daoFactory.getPersonDAO();
    }

    @Override
    public Collection<Person> getAllPeople() {
        Collection<Person> temp = null;
        try {
            temp = personDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
