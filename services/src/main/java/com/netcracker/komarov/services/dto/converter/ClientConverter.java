package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.ClientDTO;
import org.springframework.stereotype.Component;

@Component
public class ClientConverter implements Converter<ClientDTO, Client> {
    @Override
    public ClientDTO convertToDTO(Client client) {
        ClientDTO clientDTO = null;
        if (client != null) {
            clientDTO = new ClientDTO();
            Person person = client.getPerson();
            clientDTO.setId(client.getId());
            clientDTO.setName(person.getName());
            clientDTO.setSurname(person.getSurname());
            clientDTO.setLogin(person.getLogin());
            clientDTO.setPassportId(person.getPassportId());
            clientDTO.setPassword(person.getPassword());
            clientDTO.setPhoneNumber(person.getPhoneNumber());
            clientDTO.setRole(person.getRole());
        }
        return clientDTO;
    }

    @Override
    public Client convertToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        Person person = new Person();
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setPassportId(dto.getPassportId());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setLogin(dto.getLogin());
        person.setPassword(dto.getPassword());
        person.setRole(Role.CLIENT);
        client.setPerson(person);
        return client;
    }
}
