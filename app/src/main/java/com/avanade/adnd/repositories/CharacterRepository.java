package com.avanade.adnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avanade.adnd.entities.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {

}

