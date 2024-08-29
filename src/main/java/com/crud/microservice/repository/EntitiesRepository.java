package com.crud.microservice.repository;

import com.crud.microservice.entity.EntitiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntitiesRepository extends JpaRepository<EntitiesEntity, Long> {
}
