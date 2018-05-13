package com.netcracker.komarov.services.impl;

import com.netcracker.komarov.dao.entity.Person;
import com.netcracker.komarov.dao.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PersonRepository personRepository;

    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.err.println("Username: " + username);
        Person person = personRepository.findPersonByUsername(username);
        System.err.println("Person: " + person);
        return new User(person.getUsername(), person.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(person.getRole().name())));
    }
}
