package dev3.bank.interfaces;

import dev3.bank.entity.Admin;
import dev3.bank.entity.Person;

import java.util.Collection;

public interface AdminService extends BaseService {
    Admin addAdmin(Person person);

    Collection<Admin> getAllAdmin();
}
