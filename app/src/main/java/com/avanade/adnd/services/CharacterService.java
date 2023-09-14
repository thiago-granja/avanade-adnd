package com.avanade.adnd.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avanade.adnd.repositories.CharacterRepository;
import com.avanade.adnd.entities.Character;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<Character> getAllCharacters() {
        return characterRepository.findAll();
    }

    public Optional<Character> getCharacterById(Long id) {
        return characterRepository.findById(id);
    }

    @Transactional
    public Character createCharacter(Character character) {
        if(characterRepository.findByName(character.getName()).isPresent()) {
            throw new IllegalArgumentException("Personagem com nome " + character.getName() + " já existe");
        }
        return characterRepository.save(character);
    }

    @Transactional
    public Character updateCharacter(Character character) {
        if(!characterRepository.existsById(character.getId())) {
            throw new EntityNotFoundException("Personagem com ID " + character.getId() + " não encontrado");
        }
        return characterRepository.save(character);
    }

    public Optional<Character> getCharacterByName(String name) {
        return characterRepository.findByName(name);
    }

    @Transactional
    public void deleteCharacter(Long id) {
        Optional<Character> characterOptional = characterRepository.findById(id);

        if (!characterOptional.isPresent()) {
            throw new EntityNotFoundException("Personagem com ID " + id + " não encontrado");
        }

        try {
            characterRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalStateException("Não é possível deletar o personagem pois ele tem registros relacionados a ele.");
        }
    }
}
