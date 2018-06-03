package com.netcracker.komarov.services.security.utils;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.PersonRepository;
import com.netcracker.komarov.services.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private PersonRepository personRepository;

    @Autowired
    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findPersonByUsername(username);
        CustomUser customUser = null;
        if (person != null) {
            customUser = new CustomUser(person.getUsername(), person.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())), person.getId());
        }
        return customUser;
    }
}
