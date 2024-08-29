package com.crud.microservice.repository;

import com.crud.microservice.entity.UsersJwtEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UsersJwtEntity, Long> {
    Optional<UsersJwtEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
