package com.netcracker.komarov.services.interfaces;

import com.netcracker.komarov.dao.entity.Admin;
import com.netcracker.komarov.dao.entity.Person;

import java.util.Collection;

public interface AdminService {
    Admin addAdmin(Person person);

    Collection<Admin> getAllAdmin();
}
