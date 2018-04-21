package dev3.bank.impl;

import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.entity.Person;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.PersonService;
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
