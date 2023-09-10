package com.avanade.adnd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.services.BattleService;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping
    public ResponseEntity<Battle> createBattle(@RequestBody Battle battle) {
        Battle createdBattle = battleService.createBattle(battle);
        return new ResponseEntity<>(createdBattle, HttpStatus.CREATED);
    }
}
