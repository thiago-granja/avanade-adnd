package com.avanade.adnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avanade.adnd.entities.BattleParticipant;

@Repository
public interface BattleParticipantRepository extends JpaRepository<BattleParticipant, Long> {
    
}
