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

    public String getNextStep(Battle battle){
        String nextStep = battle.getNextStep();
        return nextStep;
    }

    public Boolean checkValidStep(String current, String request){
        if (current == request) return true;
        return false;
    }

    public String nextStepErrorMessage(String nextStep){
        String response = "";

        switch(nextStep) {
            case "attack":
                response = "Você deve atacar agora";
                break;
            
            case "defend":
                response = "Você deve se defender agora";
                break;

            case "damage":
                response = "Você deve fazer a rolagem de dano";
                break;

            default:
                break;
        }

        return response;
    }
}
