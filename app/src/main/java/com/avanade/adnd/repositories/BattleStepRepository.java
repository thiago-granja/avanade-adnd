package com.avanade.adnd.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.BattleStep;

public interface BattleStepRepository extends JpaRepository<BattleStep, UUID> {
    
}

