package com.avanade.adnd.services;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avanade.adnd.repositories.CharacterRepository;
import com.avanade.adnd.entities.Character;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Character registerCharacter(Character character) {
            Set<ConstraintViolation<Character>> violations = validator.validate(character);
            
            if (!violations.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (ConstraintViolation<Character> violation : violations) {
                    sb.append(violation.getMessage()).append(". ");
                }
                throw new IllegalArgumentException(sb.toString());
            }

            if(characterRepository.findByName(character.getName()).isPresent()) {
                throw new IllegalArgumentException("Um personagem com esse nome já existe");
            }

            return characterRepository.save(character);
    }

}
