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

    @Query("select a from Account a where a.locked = :locked and a.client.id = :client_id")
    Collection<Account> findAccountsByLockedAndClientId(@Param("client_id") long clientId,
                                                        @Param("locked") boolean locked);

    @Query("select a from Account a where a.client.id = :client_id")
    Collection<Account> findAccountsByClientId(@Param("client_id") long clientId);

    Account findAccountByAccountId(long accountId);
}
