package com.avanade.adnd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.avanade.adnd.entities.Character;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

}

