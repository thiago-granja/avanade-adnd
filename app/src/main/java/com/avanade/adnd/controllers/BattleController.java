package com.avanade.adnd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avanade.adnd.dtos.BattleDTO;
import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.services.BattleService;

@RestController
@RequestMapping("/battle")
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping
    public ResponseEntity<BattleDTO> createBattle(@RequestBody BattleDTO battleDTO) {
        BattleDTO createdBattleDTO = battleService.generateBattle(battleDTO);
        return new ResponseEntity<>(createdBattleDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Battle>> getAllActiveBattles() {
        List<Battle> activeBattles = battleService.getAllActiveBattles();
        if (activeBattles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(activeBattles);
    }

    
}
