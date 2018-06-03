package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.TestConfig;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PersonServiceImplTest {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private PersonService personService;

    @Before
    public void init() {
        PersonDTO personDTO1 = new PersonDTO(0L, "Tony", "Stark",
                "1", "1", "Iron_Man", "Jarvis", Role.ADMIN);
        PersonDTO personDTO2 = new PersonDTO(0L, "Steve", "Rodgers",
                "2", "2", "Captain_America", "shield", Role.CLIENT);
        PersonDTO personDTO3 = new PersonDTO(0L, "Stephen", "Strange",
                "3", "3", "Doctor_Strange", "shamballa", Role.CLIENT);
        PersonDTO personDTO4 = new PersonDTO(0L, "Peter", "Parker",
                "4", "4", "Spider-man", "Mary_Jane", Role.ADMIN);
        personService.save(personDTO1);
        personService.save(personDTO2);
        personService.save(personDTO3);
        personService.save(personDTO4);
    }


    @Test
    public void findAllPeople() {
        PersonDTO personDTO1 = new PersonDTO(1L, "Tony", "Stark",
                "1", "1", "Iron_Man", null, null);
        PersonDTO personDTO2 = new PersonDTO(2L, "Steve", "Rodgers",
                "2", "2", "Captain_America", null, null);
        PersonDTO personDTO3 = new PersonDTO(3L, "Stephen", "Strange",
                "3", "3", "Doctor_Strange", null, null);
        PersonDTO personDTO4 = new PersonDTO(4L, "Peter", "Parker",
                "4", "4", "Spider-man", null, null);
        Collection<PersonDTO> dtos = new ArrayList<>();
        dtos.add(personDTO1);
        dtos.add(personDTO2);
        dtos.add(personDTO3);
        dtos.add(personDTO4);
        assertEquals(dtos, personService.findAllPeople());
    }

    @Test
    public void findById() {
        PersonDTO personDTO = new PersonDTO(3L, "Stephen", "Strange",
                "3", "3", "Doctor_Strange", null);
        assertEquals(personDTO, personService.findById(3));
    }
}