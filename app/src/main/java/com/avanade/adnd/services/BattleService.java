package com.avanade.adnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.repositories.BattleRepository;
   
@Service
public class BattleService {

    @Autowired
    private BattleRepository battleRepository;

    public Battle createBattle(Battle battle) {
        return battleRepository.save(battle);
    }
}
