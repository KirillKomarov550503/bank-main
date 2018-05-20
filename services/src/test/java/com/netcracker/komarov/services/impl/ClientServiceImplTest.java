package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.ServiceContext;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.ClientService;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceContext.class)
public class ClientServiceImplTest {
    @Autowired
    private ClientService clientService;

    @Before
    public void init() {
        PersonDTO dto1 = new PersonDTO(0, "Max", "Ul",
                1, 1, "Pessimist", "password");
        PersonDTO dto2 = new PersonDTO(0, "Pav", "Zar",
                2, 2, "Dancer", "disco");
        PersonDTO dto3 = new PersonDTO(0, "Kir", "Kom",
                3, 3, "Optimist", "qwerty");
        clientService.save(dto1);
        clientService.save(dto2);
        clientService.save(dto3);
    }

    @Test
    public void save() {
        PersonDTO personDTO = new PersonDTO(0, "Tony", "Stark",
                4444, 9, "Iron_man", "Jarvis");
        ClientDTO clientDTO = new ClientDTO(4, "Tony", "Stark",
                "Iron_man", "Jarvis", 4444, 9, Role.CLIENT);
        assertEquals(clientDTO, clientService.save(personDTO));
    }

    @Test
    public void findAllClients() {
        Collection<ClientDTO> clients = new ArrayList<>();
        ClientDTO dto1 = new ClientDTO(1, "Max", "Ul",
                "Pessimist", "password", 1, 1, Role.CLIENT);
        ClientDTO dto2 = new ClientDTO(2, "Pav", "Zar",
                "Dancer", "disco", 2, 2, Role.CLIENT);
        ClientDTO dto3 = new ClientDTO(3, "Kir", "Kom",
                "Optimist", "qwerty", 3, 3, Role.CLIENT);
        clients.add(dto1);
        clients.add(dto2);
        clients.add(dto3);
        assertEquals(clients, clientService.findAllClients());
    }

    @Test
    public void update() {
        ClientDTO dto = new ClientDTO(3, "Kirill", "Komarov",
                "Optimist", "qwerty", 3, 3, Role.CLIENT);
        ClientDTO res = new ClientDTO(3, "Kirill", "Komarov",
                "Optimist", "qwerty", 3, 3, Role.CLIENT);
        assertEquals(res, clientService.update(dto));
    }

    @Test
    public void delete() {
        clientService = mock(ClientService.class);
        doNothing().when(clientService).deleteById(isA(Long.class));
        clientService.deleteById(14243);
        verify(clientService, times(1)).deleteById(14243);
    }

    @Test
    public void findById() {
        ClientDTO clientDTO = new ClientDTO(2, "Pav", "Zar",
                "Dancer", "disco", 2, 2, Role.CLIENT);
        assertEquals(clientDTO, clientService.findById(2));
    }
}