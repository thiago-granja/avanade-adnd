package com.avanade.adnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.BattleParticipant;

public interface BattleParticipantRepository extends JpaRepository<BattleParticipant, Long> {
    
}
