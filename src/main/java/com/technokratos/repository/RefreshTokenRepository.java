package com.technokratos.repository;


import com.technokratos.model.UserRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<UserRefreshTokenEntity, UUID> {
}
