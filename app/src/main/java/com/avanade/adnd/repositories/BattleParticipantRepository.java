package com.avanade.adnd.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avanade.adnd.entities.BattleParticipant;

public interface BattleParticipantRepository extends JpaRepository<BattleParticipant, Long> {

 @Query(value = "SELECT * FROM battle_participant bp WHERE bp.battle_id = :battleId AND bp.hp > 0 ORDER BY bp.initiative DESC", nativeQuery = true)
    List<BattleParticipant> findActiveParticipants(@Param("battleId") UUID battleId);
}

