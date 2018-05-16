package com.netcracker.komarov.controllers.util;

import com.netcracker.komarov.services.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@Configuration
@ComponentScan("com.netcracker.komarov")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String PREFIX = "/bank/v1";
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private CustomPasswordEncoder customPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(customPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PREFIX + "/admins*", PREFIX + "/admins/**").hasAuthority("ADMIN")
                .antMatchers(PREFIX + "/clients*", PREFIX + "/clients/**").hasAuthority("CLIENT")
                .antMatchers(PREFIX + "/news*", PREFIX + "/news/**").permitAll()
                .antMatchers(PREFIX + "/registration*").anonymous()
                .and()
                .httpBasic()
                .and()
                .logout()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();
    }
}
