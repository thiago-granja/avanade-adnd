package com.avanade.adnd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avanade.adnd.entities.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByName(String name);
    
    @Query(nativeQuery = true, value = "SELECT * FROM Character WHERE type = 'monster' ORDER BY RAND() LIMIT 1")
    Optional<Character> findRandomMonster();
}

