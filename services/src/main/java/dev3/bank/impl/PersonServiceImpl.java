package dev3.bank.impl;

import dev3.bank.dao.interfaces.PersonDAO;
import dev3.bank.entity.Person;
import dev3.bank.factory.DAOFactory;
import dev3.bank.interfaces.PersonService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Collection;

@Service
public class PersonServiceImpl implements PersonService {
    private PersonDAO personDAO;
    private static PersonServiceImpl personService;

    private PersonServiceImpl() {
    }

    public static synchronized PersonServiceImpl getPersonService() {
        if (personService == null) {
            personService = new PersonServiceImpl();
        }
        return personService;
    }

    @Override
    public void setDAO(DAOFactory daoFactory) {
        personDAO = daoFactory.getPersonDAO();
    }

    @Override
    public Collection<Person> getAllPeople() {
        Collection<Person> temp = null;
        try{
            temp = personDAO.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
