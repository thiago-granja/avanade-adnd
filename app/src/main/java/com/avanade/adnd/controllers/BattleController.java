package com.avanade.adnd.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public ResponseEntity<Battle> getBattleById(@PathVariable UUID id) {
        return battleService.getBattleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{battle_id}/logs")
    public ResponseEntity<Object> getBattleLogs(@PathVariable UUID battle_id) {
        return battleService.getLogsForBattle(battle_id);
    }
    
}
