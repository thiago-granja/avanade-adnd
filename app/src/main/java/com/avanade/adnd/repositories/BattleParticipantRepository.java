package com.avanade.adnd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.entities.BattleParticipant;

public interface BattleParticipantRepository extends JpaRepository<BattleParticipant, Long> {

    @Query("SELECT bp FROM BattleParticipant bp WHERE bp.battle = :battle AND bp.hp > 0 ORDER BY bp.initiative DESC")
    List<BattleParticipant> findActiveParticipants(@Param("battle") Battle battle);
}

