package com.avanade.adnd.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avanade.adnd.entities.BattleStep;

@Repository
public interface BattleStepRepository extends JpaRepository<BattleStep, UUID> {
    
}

