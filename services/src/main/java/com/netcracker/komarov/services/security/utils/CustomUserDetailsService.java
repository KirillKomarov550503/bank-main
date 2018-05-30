package com.netcracker.komarov.services.security.utils;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.entity.Role;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.dto.entity.PersonDTO;
import com.netcracker.komarov.services.interfaces.PersonService;
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
    private PersonService personService;
    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Value("${admin.name}")
    private String adminName;

    @Value("${admin.surname}")
    private String adminSurname;

    @Value("${admin.passportId}")
    private String adminPassportId;

    @Value("${admin.phoneNumber}")
    private String adminPhoneNumber;

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository, PersonService personService) {
        this.personRepository = personRepository;
        this.personService = personService;
    }

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
                temp.setRole(Role.ADMIN);
                PersonDTO dto = personService.save(temp);
                System.err.println("SuperAdmin: " + dto);
                customUser = new CustomUser(dto.getUsername(), dto.getPassword(),
                        Collections.singletonList(new SimpleGrantedAuthority("ADMIN")), dto.getId());
            } else {
                customUser = new CustomUser(person.getUsername(), person.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())), person.getId());
            }
        } else {
            customUser = new CustomUser(person.getUsername(), person.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())), person.getId());
        }
        return customUser;
    }
}
