package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.services.dto.converter.AdminConverter;
import com.netcracker.komarov.services.dto.converter.PersonConverter;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private AdminConverter adminConverter;
    private PersonConverter personConverter;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    public AdminServiceImpl(RepositoryFactory repositoryFactory, AdminConverter adminConverter, PersonConverter personConverter) {
        this.adminRepository = repositoryFactory.getAdminRepository();
        this.adminConverter = adminConverter;
        this.personConverter = personConverter;
    }

    private Collection<AdminDTO> convertCollection(Collection<Admin> admins) {
        return admins.stream()
                .map(admin -> adminConverter.convertToDTO(admin))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AdminDTO addAdmin(PersonDTO personDTO) {
        Person person = personConverter.convertToEntity(personDTO);
        person.setRole(Role.ADMIN);
        Admin admin = new Admin();
        admin.setPerson(person);
        person.setAdmin(admin);
        Admin adminRes = adminRepository.save(admin);
        logger.info("Add to system new admin");
        return adminConverter.convertToDTO(adminRes);
    }

    @Transactional
    @Override
    public Collection<AdminDTO> getAllAdmin() {
        logger.info("Return all admins");
        return convertCollection(adminRepository.findAll());
    }
}
