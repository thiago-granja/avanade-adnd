package com.avanade.adnd.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.entities.BattleLog;

public interface BattleLogRepository extends JpaRepository<BattleLog, UUID> {
    List<BattleLog> findByBattleOrderByCreatedAtAsc(Battle battle);
}
