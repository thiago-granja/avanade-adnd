package com.avanade.adnd.controllers;

import com.avanade.adnd.entities.BattleParticipant;
import com.avanade.adnd.services.BattleParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/battle-participants")
public class BattleParticipantController {

    @Autowired
    private BattleParticipantService battleParticipantService;

    @PostMapping
    public ResponseEntity<BattleParticipant> addParticipant(@RequestBody BattleParticipant participant) {
        BattleParticipant addedParticipant = battleParticipantService.addParticipant(participant);
        return new ResponseEntity<>(addedParticipant, HttpStatus.CREATED);
    }
}
