package com.init.user_management.repository;

import com.init.user_management.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    boolean existsByUserName(String userName);
    Optional<Users> findByUserName(String userName);
}
