package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Collection<Transaction> findTransactionsByAccountFromId(long accountFromId);

    Collection<Transaction> findTransactionsByAccountToId(long accountToId);

    @Query("select t from Transaction t where t.accountFromId in " +
            "(select a.id from Account a where a.client.id = :client_id)")
    Collection<Transaction> findTransactionsByClientId(@Param("client_id") long clientId);
}

