package com.avanade.adnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avanade.adnd.services.CharacterService;
import com.avanade.adnd.entities.Character;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping("/new")
    public ResponseEntity<Character> registerCharacter(@RequestBody Character character) {
        Character savedCharacter = characterService.save(character);
        return new ResponseEntity<>(savedCharacter, HttpStatus.CREATED);
    }
}
