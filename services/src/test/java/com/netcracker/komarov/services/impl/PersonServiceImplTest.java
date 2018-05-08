package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import com.netcracker.komarov.services.interfaces.ClientService;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class PersonServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PersonService personService;

    @Before
    public void init() {
        PersonDTO personDTO1 = new PersonDTO(0, "Tony", "Stark", 1, 1);
        PersonDTO personDTO2 = new PersonDTO(0, "Steve", "Rodgers", 2, 2);
        PersonDTO personDTO3 = new PersonDTO(0, "Stephen", "Strange", 3, 3);
        PersonDTO personDTO4 = new PersonDTO(0, "Peter", "Parker", 4, 4);
        clientService.save(personDTO2);
        adminService.addAdmin(personDTO1);
        adminService.addAdmin(personDTO4);
        clientService.save(personDTO3);
    }


    @Test
    public void getAllPeople() {
        PersonDTO personDTO1 = new PersonDTO(2, "Tony", "Stark", 1, 1);
        PersonDTO personDTO2 = new PersonDTO(1, "Steve", "Rodgers", 2, 2);
        PersonDTO personDTO3 = new PersonDTO(4, "Stephen", "Strange", 3, 3);
        PersonDTO personDTO4 = new PersonDTO(3, "Peter", "Parker", 4, 4);
        Collection<PersonDTO> dtos = new ArrayList<>();
        dtos.add(personDTO2);
        dtos.add(personDTO1);
        dtos.add(personDTO4);
        dtos.add(personDTO3);
        Assert.assertEquals(dtos, personService.getAllPeople());
    }
}