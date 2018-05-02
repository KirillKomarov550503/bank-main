package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Collection<Card> findCardsByLocked(boolean locked);

    @Query("select c from Card c where c.account.id = :account_id")
    Collection<Card> findCardsByAccountId(@Param("account_id") long accountId);

    @Query("select c from Card c where c.account.client.id = :client_id and c.locked = :locked")
    Collection<Card> findCardsByClientIdAndLocked(@Param("client_id") long clientId, @Param("locked") boolean locked);

    @Query("select c from Card c where c.account.client.id = :client_id")
    Collection<Card> findCardsByClientId(@Param("client_id") long clientId);

}
