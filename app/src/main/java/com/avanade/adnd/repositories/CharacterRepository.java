package com.avanade.adnd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByName(String name);
    
}

