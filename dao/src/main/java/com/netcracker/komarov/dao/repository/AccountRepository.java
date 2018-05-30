package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Collection<Account> findAccountsByLocked(boolean locked);

    @Query("select a from Account a where a.locked = :locked and a.person.id = :person_id")
    Collection<Account> findAccountsByLockedAndPersonId(@Param("person_id") long personId,
                                                        @Param("locked") boolean locked);

    @Query("select a from Account a where a.person.id = :person_id")
    Collection<Account> findAccountsByPersonId(@Param("person_id") long personId);

}
