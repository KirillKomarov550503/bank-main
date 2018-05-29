package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.converter.AdminConverter;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.exception.LogicException;
import com.netcracker.komarov.services.exception.NotFoundException;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private PersonRepository personRepository;
    private AdminRepository adminRepository;
    private AdminConverter adminConverter;
    private PersonConverter personConverter;
    private PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    public AdminServiceImpl(PersonRepository personRepository, AdminRepository adminRepository,
                            AdminConverter adminConverter, PersonConverter personConverter,
                            PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.adminRepository = adminRepository;
        this.adminConverter = adminConverter;
        this.personConverter = personConverter;
        this.passwordEncoder = passwordEncoder;
    }

    private Collection<AdminDTO> convertCollection(Collection<Admin> admins) {
        return admins.stream()
                .map(admin -> adminConverter.convertToDTO(admin))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AdminDTO saveAdmin(PersonDTO personDTO) throws LogicException {
        Person person = personConverter.convertToEntity(personDTO);
        person.setRole(Role.ADMIN);
        Person temp = personRepository.findPersonByUsername(person.getUsername());
        Admin adminRes;
        if (temp == null) {
            String password = person.getPassword();
            person.setPassword(passwordEncoder.encode(password));
            Admin admin = new Admin();
            admin.setPerson(person);
            person.setAdmin(admin);
            adminRes = adminRepository.save(admin);
            LOGGER.info("Add to system new admin with ID " + adminRes);
        } else {
            String error = "This username is already exist";
            LOGGER.error(error);
            throw new LogicException(error);

        }
        return adminConverter.convertToDTO(adminRes);
    }

    @Transactional
    @Override
    public Collection<AdminDTO> findAllAdmins() {
        LOGGER.info("Return all admins");
        return convertCollection(adminRepository.findAll());
    }

    @Transactional
    @Override
    public AdminDTO update(AdminDTO adminDTO) throws NotFoundException {
        Admin newClient = adminConverter.convertToEntity(adminDTO);
        Optional<Admin> optionalAdmin = adminRepository.findById(adminDTO.getId());
        Admin resAdmin;
        if (optionalAdmin.isPresent()) {
            Admin oldAdmin = optionalAdmin.get();
            Person oldPerson = oldAdmin.getPerson();
            Person newPerson = newClient.getPerson();
            String password = newPerson.getPassword();
            newPerson.setPassword(passwordEncoder.encode(password));
            newPerson.setId(oldPerson.getId());
            oldAdmin.setPerson(newPerson);
            resAdmin = adminRepository.saveAndFlush(oldAdmin);
            LOGGER.info("Information about admin with ID " + adminDTO.getId() + " was updated");
        } else {
            String error = "There is no such admin in database with ID " + adminDTO.getId();
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return adminConverter.convertToDTO(resAdmin);
    }

    @Transactional
    @Override
    public void deleteById(long adminId) throws NotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        if (optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            personRepository.deleteById(admin.getPerson().getId());
            LOGGER.info("Client with ID " + adminId + " was deleted");
        } else {
            String error = "There is no such admin in database with ID " + adminId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
    }

    @Override
    public AdminDTO findById(long adminId) throws NotFoundException {
        Optional<Admin> optionalAdmin = adminRepository.findById(adminId);
        Admin admin;
        if (optionalAdmin.isPresent()) {
            admin = optionalAdmin.get();
            LOGGER.info("Return admin with ID " + adminId);
        } else {
            String error = "There is no such admin with ID " + adminId;
            LOGGER.error(error);
            throw new NotFoundException(error);
        }
        return adminConverter.convertToDTO(admin);
    }
}
