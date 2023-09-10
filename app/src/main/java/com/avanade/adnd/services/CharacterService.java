package com.avanade.adnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avanade.adnd.repositories.CharacterRepository;
import com.avanade.adnd.entities.Character;

@Service
public class CharacterService {
    //private int MAX_STATS = 30;

    @Autowired
    private CharacterRepository characterRepository;

    public Character save(Character character) {
        return characterRepository.save(character);
    }
}
