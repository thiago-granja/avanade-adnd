package com.avanade.adnd.services;

import com.avanade.adnd.entities.BattleParticipant;
import com.avanade.adnd.entities.BattleStep;
import com.avanade.adnd.entities.Character;
import com.avanade.adnd.repositories.BattleStepRepository;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleStepService {

    @Autowired
    private BattleStepRepository battleStepRepository;

    public BattleStep addStep(BattleStep step) {
        return battleStepRepository.save(step);
    }

    Short[] rollDice(Short dice, Short faces) throws IllegalArgumentException {
        short MIN = 1;
        short MAX_DIE_FACES = 20;
        short MAX_DIE_COUNT = 10;
    
        if (dice < MIN || dice > MAX_DIE_COUNT) {
            throw new IllegalArgumentException("Você deve jogar de 1 a 10 dados");
        }
        if (faces < MIN || faces > MAX_DIE_FACES) {
            throw new IllegalArgumentException("Os dados devem ter de 1 a 20 lados");
        }
    
        Short rolls[] = new Short[dice];
        Short minRoll = 1;
        Short maxRoll = faces;
    
        Random random = new Random();
        for (int i = 0; i < dice; i++) {
            Short roll = (short) (minRoll + random.nextInt(maxRoll - minRoll + 1));
            rolls[i] = roll;
        }
        return rolls;
    }
    

    Integer rollInitiative(BattleParticipant battleParticipant) {
        Integer total = 0;
        Short[] roll = rollDice((short) 1, (short) 20);
        for (int i = 0; i < roll.length; i++) {
            total += roll[i];
        }
        battleParticipant.setInitiative(roll[0]);
        return total;
    }

    Boolean checkValidInitiative(BattleParticipant player, BattleParticipant computer) {
        if (player.getInitiative() != computer.getInitiative()) return true;
        return false;
    }

    
}
