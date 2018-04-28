package com.netcracker.komarov.dao.repository;

import com.netcracker.komarov.dao.entity.News;
import com.netcracker.komarov.dao.entity.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("select n from News n where n.admin.id = :admin_id")
    Collection<News> findNewsByAdminId(@Param("admin_id") long adminId);

    Collection<News> findNewsByNewsStatus(NewsStatus newsStatus);
}
