package com.avanade.adnd.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avanade.adnd.entities.Battle;

@Repository
public interface BattleRepository extends JpaRepository<Battle, UUID> {

}