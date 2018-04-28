package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UnlockAccountRequestRepository extends JpaRepository<UnlockAccountRequest, Long> {
    @Query("select u from UnlockAccountRequest u where u.account.id = :account_id")
    UnlockAccountRequest findUnlockAccountRequestByAccountId(@Param("account_id") long accountId);

    @Transactional
    @Modifying
    @Query("delete from UnlockAccountRequest u where u.account.id = :account_id")
    void deleteByAccountId(@Param("account_id") long accountId);
}

