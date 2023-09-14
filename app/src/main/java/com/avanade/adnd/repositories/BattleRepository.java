package com.avanade.adnd.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.Battle;

public interface BattleRepository extends JpaRepository<Battle, UUID> {
    List<Battle> findByIsActiveTrue();
}