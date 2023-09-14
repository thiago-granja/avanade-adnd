package com.avanade.adnd.controllers;

import com.avanade.adnd.dtos.BattleDTO;
import com.avanade.adnd.services.BattleStepService;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/battle")
public class BattleStepController {

    @Autowired
    private BattleStepService battleStepService;

    @PostMapping("/{battle_id}/{operation}")
    public ResponseEntity<?> processBattleStep(@PathVariable UUID battle_id, @PathVariable String operation) {

        if (!Arrays.asList("attack", "defense", "initiative", "damage").contains(operation)) {
            return new ResponseEntity<>("Movimento inválido. As opcões disponíveis são attack, defense, damage, initiative.", HttpStatus.BAD_REQUEST);
        }

        BattleDTO battleResult = battleStepService.runBattleStep(battle_id, operation);
        return new ResponseEntity<>(battleResult, HttpStatus.OK);
    }

}