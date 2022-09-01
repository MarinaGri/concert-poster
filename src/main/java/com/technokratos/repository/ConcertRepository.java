package com.technokratos.repository;

import com.technokratos.model.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConcertRepository extends JpaRepository<ConcertEntity, UUID> {
}
