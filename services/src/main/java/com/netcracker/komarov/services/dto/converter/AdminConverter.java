package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter implements Converter<AdminDTO, Admin> {
    @Override
    public AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = null;
        if (admin != null) {
            adminDTO = new AdminDTO();
            Person person = admin.getPerson();
            adminDTO.setId(admin.getId());
            adminDTO.setName(person.getName());
            adminDTO.setSurname(person.getSurname());
            adminDTO.setLogin(person.getUsername());
            adminDTO.setPassportId(person.getPassportId());
            adminDTO.setPhoneNumber(person.getPhoneNumber());
        }
        return adminDTO;
    }

    @Override
    public Admin convertToEntity(AdminDTO dto) {
        Admin admin = new Admin();
        admin.setId(dto.getId());
        Person person = new Person();
        person.setName(dto.getName());
        person.setSurname(dto.getSurname());
        person.setPassportId(dto.getPassportId());
        person.setPhoneNumber(dto.getPhoneNumber());
        person.setUsername(dto.getLogin());
        person.setPassword(dto.getPassword());
        person.setRole(Role.ADMIN);
        admin.setPerson(person);
        return admin;
    }
}
