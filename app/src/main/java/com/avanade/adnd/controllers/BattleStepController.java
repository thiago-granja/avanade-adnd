package com.avanade.adnd.controllers;

import com.avanade.adnd.dtos.BattleDTO;
import com.avanade.adnd.services.BattleStepService;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/battle")
public class BattleStepController {

    @Autowired
    private BattleStepService battleStepService;

    @PostMapping("/{battle_id}/attack")
    public BattleDTO attack(@PathVariable UUID battle_id) {
        return battleStepService.runBattleStep(battle_id, "attack");
    }

    @PostMapping("/{battle_id}/defend")
    public BattleDTO defend(@PathVariable UUID battle_id) {
        return battleStepService.runBattleStep(battle_id, "defend");
    }

    @PostMapping("/{battle_id}/damage")
    public BattleDTO damage(@PathVariable UUID battle_id) {
        return battleStepService.runBattleStep(battle_id, "damage");
    }

    @PostMapping("/{battle_id}/initiative")
    public BattleDTO initiative(@PathVariable UUID battle_id) {
        return battleStepService.runBattleStep(battle_id, "initiative");
    }
}
