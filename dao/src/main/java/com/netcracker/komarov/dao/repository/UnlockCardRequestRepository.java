package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.UnlockCardRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnlockCardRequestRepository extends JpaRepository<UnlockCardRequest, Long> {
    @Query("select u from UnlockCardRequest u where u.card.id = :card_id")
    UnlockCardRequest longByCardId(@Param("card_id") long cardId);

    @Modifying
    @Query("delete from UnlockCardRequest u where u.card.id = :card_id")
    void deleteByCardId(@Param("card_id") long card_id);
}
