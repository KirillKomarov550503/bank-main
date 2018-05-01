package com.netcracker.komarov.services.dto.converter;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.services.dto.Converter;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminConverter implements Converter<AdminDTO, Admin> {
    @Override
    public AdminDTO convertToDTO(Admin admin) {
        AdminDTO adminDTO = null;
        if(admin != null){
            adminDTO = new AdminDTO();
            Person person = admin.getPerson();
            adminDTO.setName(person.getName());
            adminDTO.setSurname(person.getSurname());
            adminDTO.setLogin(person.getLogin());
            adminDTO.setPassportId(person.getPassportId());
            adminDTO.setPassword(person.getPassword());
            adminDTO.setPhoneNumber(person.getPhoneNumber());
            adminDTO.setRole(person.getRole());
        }
        return adminDTO;
    }

    @Override
    public Admin convertToEntity(AdminDTO dto) {
        return null;
    }
}
