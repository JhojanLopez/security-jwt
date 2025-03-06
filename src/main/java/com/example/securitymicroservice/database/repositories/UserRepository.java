package com.example.securitymicroservice.database.repositories;

import com.example.securitymicroservice.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    boolean existsUserByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
}
