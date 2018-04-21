package dev3.bank.interfaces;

import dev3.bank.entity.Client;
import dev3.bank.entity.Person;

import java.util.Collection;

public interface ClientService {
    Client signIn(Person person);

    Client registration(Person person);

    Collection<Client> getAllClients();
}
