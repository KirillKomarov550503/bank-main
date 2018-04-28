package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.factory.RepositoryFactory;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminRepository adminRepository;
    private Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Autowired
    public AdminServiceImpl(RepositoryFactory repositoryFactory) {
        this.adminRepository = repositoryFactory.getAdminRepository();
    }

    @Transactional
    @Override
    public Admin addAdmin(Person person) {
        Admin admin = new Admin();
        admin.setPerson(person);
        person.setAdmin(admin);
        Admin adminRes = adminRepository.save(admin);
        logger.info("Add to system new admin");
        return adminRes;
    }

    @Transactional
    @Override
    public Collection<Admin> getAllAdmin() {
        logger.info("Return all admins");
        return adminRepository.findAll();
    }
}
