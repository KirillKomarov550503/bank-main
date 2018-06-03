package com.netcracker.komarov.services.security.config;

import com.netcracker.komarov.services.security.filter.ContainFilter;
import com.netcracker.komarov.services.security.utils.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    @Autowired
    private ContainFilter containFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(customPasswordEncoder);
        return authenticationProvider;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(containFilter);
        registrationBean.addUrlPatterns(PREFIX + "/clients/*", PREFIX + "/admins/*", PREFIX + "/people/*");
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PREFIX + "/admins*", PREFIX + "/admins/**").hasAuthority("ADMIN")
                .antMatchers(PREFIX + "/clients*", PREFIX + "/clients/**").hasAuthority("CLIENT")
                .antMatchers(PREFIX + "/news*", PREFIX + "/news/**").permitAll()
                .antMatchers(PREFIX + "/people/**").authenticated()
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
