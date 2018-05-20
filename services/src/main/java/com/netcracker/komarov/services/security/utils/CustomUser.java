package com.netcracker.komarov.services.security.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Objects;

public class CustomUser extends User {
    private long id;


    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, long id) {
        super(username, password, authorities);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomUser that = (CustomUser) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
