package com.netcracker.komarov.services.security.utils;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Client;
import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.AdminRepository;
import com.netcracker.komarov.dao.repository.ClientRepository;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.entity.AdminDTO;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private PersonRepository personRepository;
    private ClientRepository clientRepository;
    private AdminRepository adminRepository;
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
    public CustomUserDetailsService(PersonRepository personRepository, AdminService adminService,
                                    AdminRepository adminRepository, ClientRepository clientRepository) {
        this.personRepository = personRepository;
        this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByUsername(username);
        CustomUser customUser;
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
                customUser = new CustomUser(dto.getLogin(), dto.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN")), dto.getId());
            } else {
                customUser = new CustomUser(person.getUsername(), person.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())), getId(person));
            }
        } else {
            customUser = new CustomUser(person.getUsername(), person.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())), getId(person));
        }
        return customUser;
    }

    private long getId(Person person) {
        Admin admin = adminRepository.findAdminByPersonId(person.getId());
        Client client = clientRepository.findClientByPersonId(person.getId());
        long id = 0;
        if (admin != null) {
            id = admin.getId();
        } else if (client != null) {
            id = client.getId();
        }
        return id;
    }
}
