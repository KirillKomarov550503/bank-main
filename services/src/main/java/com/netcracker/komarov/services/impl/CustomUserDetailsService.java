package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private PersonRepository personRepository;
    private AdminService adminService;
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.surname}")
    private String adminSurname;

    @Value("${admin.passportId}")
    private long adminPassportId;

    @Value("${admin.phoneNumber}")
    private long adminPhoneNumber;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository, AdminService adminService) {
        this.personRepository = personRepository;
        this.adminService = adminService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Username: " + username);
        Person person = personRepository.findPersonByUsername(username);
        User user;
        if (username.equals(adminUsername)) {
            if (person == null) {
                PersonDTO temp = new PersonDTO();
                temp.setName(adminName);
                temp.setSurname(adminSurname);
                temp.setPassportId(adminPassportId);
                temp.setPhoneNumber(adminPhoneNumber);
                temp.setUsername(adminUsername);
                temp.setPassword(adminPassword);
                AdminDTO dto = adminService.addAdmin(temp);
                user = new User(dto.getLogin(), dto.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
            } else {
                user = new User(person.getUsername(), person.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())));
            }
        } else {
            user = new User(person.getUsername(), person.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())));
        }
        return user;
    }
}
