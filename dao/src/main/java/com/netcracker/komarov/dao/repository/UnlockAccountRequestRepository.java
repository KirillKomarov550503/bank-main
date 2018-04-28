package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.UnlockAccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UnlockAccountRequestRepository extends JpaRepository<UnlockAccountRequest, Long> {
    @Query("select u from UnlockAccountRequest u where u.account.id = :account_id")
    UnlockAccountRequest findUnlockAccountRequestByAccountId(@Param("account_id") long accountId);
}

