package com.init.file_management.repository;

import com.init.file_management.dto.response.StatusCount;
import com.init.file_management.entity.Document;
import com.init.file_management.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, JpaSpecificationExecutor {
    @Query("SELECT new com.init.file_management.dto.response.StatusCount(d.status, COUNT(d)) " +
            "FROM Document d " +
            "GROUP BY d.status")
    List<StatusCount> countDocumentsByStatus();
}
