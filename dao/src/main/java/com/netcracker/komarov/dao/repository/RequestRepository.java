package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Modifying
    @Query("delete from Request r where r.id = :request_id")
    void deleteRequestById(@Param("request_id") long requestId);

    Request findRequestByAccount_Id(long account_id);

    Request findRequestByCard_Id(long card_id);
}
