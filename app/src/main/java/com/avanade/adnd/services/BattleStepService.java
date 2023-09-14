package com.avanade.adnd.services;

import com.avanade.adnd.entities.BattleStep;
import com.avanade.adnd.repositories.BattleParticipantRepository;
import com.avanade.adnd.repositories.BattleRepository;
import com.avanade.adnd.repositories.BattleStepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleStepService {

    @Autowired
    private BattleStepRepository battleStepRepository;


    @Autowired
    private DiceRollerService diceRollerService;

    @Autowired
    private BattleParticipantRepository battleParticipantRepository;

    @Autowired
    private BattleRepository battleRepository;

    public BattleStep addStep(BattleStep step) {
        return battleStepRepository.save(step);
    }


}
