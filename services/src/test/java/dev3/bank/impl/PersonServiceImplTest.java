package dev3.bank.impl;

import dev3.bank.dao.utils.DataBase;
import dev3.bank.entity.Person;
import dev3.bank.entity.Role;
import dev3.bank.interfaces.PersonService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;

public class PersonServiceImplTest extends AbstractSpringTest {

    @Autowired
    private PersonService personService;

    @Before
    public void init() {
        DataBase.initTable();
        DataBase.insertValues();
    }

    @After
    public void destroy() {
        DataBase.dropTable();
    }

    @Test
    public void getAllPeople() {
        Person person1 = new Person(1, "Kirill", "Komarov", 123, Role.CLIENT,
                null, null, 4234);
        Person person2 = new Person(2, "Vladislav", "Maznya", 54, Role.CLIENT,
                null, null, 234);
        Person person3 = new Person(3, "Pavel", "Zaretskya", 1252, Role.CLIENT,
                null, null, 643);
        Person person4 = new Person(4, "Vladimir", "Putin", 1111111, Role.ADMIN,
                null, null, 1);
        Collection<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person2);
        people.add(person3);
        people.add(person4);
        Assert.assertEquals(people, personService.getAllPeople());
    }
}