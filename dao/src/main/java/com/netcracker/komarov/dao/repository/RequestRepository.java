package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findRequestByAccount_Id(long account_id);
    Request findRequestByCard_Id(long card_id);
}
