package com.avanade.adnd.services;

import com.avanade.adnd.entities.BattleStep;
import com.avanade.adnd.repositories.BattleStepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleStepService {

    @Autowired
    private BattleStepRepository battleStepRepository;

    public BattleStep addStep(BattleStep step) {
        return battleStepRepository.save(step);
    }
}
