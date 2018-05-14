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
        logger.info("Username: " + username);
        if (username.equals("superadmin")) {
            return new User(username, "$2a$04$lfTvmNsZbBiOE453VZgI4.oy5ZIctOsBJbWm8aPC.2FqMQ6oDn7.6",
                    Collections.singletonList(new SimpleGrantedAuthority("ADMIN")));
        } else {
            Person person = personRepository.findPersonByUsername(username);
            return new User(person.getUsername(), person.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority(person.getRole().name())));
        }
    }
}
