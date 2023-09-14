package com.avanade.adnd.services;

import com.avanade.adnd.dtos.BattleDTO;
import com.avanade.adnd.dtos.RollResultDTO;
import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.entities.BattleParticipant;
import com.avanade.adnd.entities.Character;
import com.avanade.adnd.repositories.BattleParticipantRepository;
import com.avanade.adnd.repositories.BattleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleStepService {

    @Autowired
    private DiceRollerService diceRollerService;

    @Autowired
    private BattleParticipantRepository battleParticipantRepository;

    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private BattleService battleService;

    public RollResultDTO rollInitiative(BattleParticipant battleParticipant) {
        Character character = battleParticipant.getCharacter();
        
        Integer[] rolls = diceRollerService.rollDice(1, 20);
        Integer roll = rolls[0];
        Integer total = roll;
    
        String message = "O " + character.getName() + " rolou [" + roll + "] para sua iniciativa.";
    
        return new RollResultDTO(Arrays.asList(roll), total, message);
    }

    public RollResultDTO rollAttack(BattleParticipant attacker) {
        Character character = attacker.getCharacter();
    
        if (character == null) {
            throw new RuntimeException("Personagem não encontrado");
        }
        
        Integer[] rolls = diceRollerService.rollDice(1, 12);
        Integer roll = rolls[0];
    
        Integer total = roll + character.getStrength() + character.getAgility();
    
        String message = "O " + character.getName() + " rolou [" + roll + "], somando " + total + " para seu ataque. ";
    
        return new RollResultDTO(Arrays.asList(roll), total, message);
    }
    
    public RollResultDTO rollDefense(BattleParticipant defender) {
        Character character = defender.getCharacter();
    
        if (character == null) {
            throw new RuntimeException("Personagem não encontrado");
        }
        
        Integer[] rolls = diceRollerService.rollDice(1, 12);
        Integer roll = rolls[0];
    
        Integer total = roll + character.getDefense() + character.getAgility();
    
        String message = "O " + character.getName() + " rolou [" + roll + "], somando " + total + " para sua defesa. ";
    
        return new RollResultDTO(Arrays.asList(roll), total, message);
    }

    public BattleDTO settleInitiatives(BattleParticipant player, BattleParticipant computer) {
        RollResultDTO playerRoll = rollInitiative(player);
        RollResultDTO computerRoll = rollInitiative(computer);
        
        while (playerRoll.getTotal() == computerRoll.getTotal()) {
            playerRoll = rollInitiative(player);
            computerRoll = rollInitiative(computer);
        }

        player.setInitiative(playerRoll.getTotal());
        computer.setInitiative(computerRoll.getTotal());

        battleParticipantRepository.save(player);
        battleParticipantRepository.save(computer);

        String winner = ( playerRoll.getTotal() > computerRoll.getTotal() 
                        ? player.getCharacter().getName() 
                        : computer.getCharacter().getName());

        BattleDTO message = new BattleDTO.Builder()
                                    .playerRoll(playerRoll.getRolls())
                                    .playerRollMessage(playerRoll.getMessage())
                                    .playerCharacter(player.getCharacter().getName())
                                    .computerCharacter(computer.getCharacter().getName())
                                    .computerRoll(computerRoll.getRolls())
                                    .computerRollMessage(computerRoll.getMessage())
                                    .message("O " + winner + " ataca primeiro!")
                                    .build();

        return message;
    }

    public RollResultDTO rollDamage(BattleParticipant attacker) {
        Character character = attacker.getCharacter();
    
        if (character == null) {
            throw new RuntimeException("Personagem não encontrado");
        }
    
        Integer[] rollArray = diceRollerService.rollDice(character.getDiceQuantity(), character.getDiceFaces());
        List<Integer> rolls = Arrays.asList(rollArray);
    
        Integer total = rolls.stream().mapToInt(Integer::intValue).sum();
        total += character.getStrength();
    
        String rollsMessage = rolls.size() > 1 ? " rolou " + rolls : " rolou [" + rolls.get(0) + "]";
        String message = "O " + character.getName() + rollsMessage + ", causando " + total + " pontos de dano em seu oponente!";
    
        return new RollResultDTO(rolls, total, message);
    }

    public void takeDamage(BattleParticipant defender, RollResultDTO damage) {
        Character character = defender.getCharacter();

        if (character == null) {
            throw new RuntimeException("Personagem não encontrado");
        }

        int remainingHp = defender.getHp() - damage.getTotal();
        if (remainingHp < 0) remainingHp = 0;
        defender.setHp(remainingHp);
        battleParticipantRepository.save(defender);
    }
    
    public Boolean checkIfAttackHit(RollResultDTO attack, RollResultDTO defense) {
        if (attack.getTotal() > defense.getTotal()) return true;
        return false;
    }

    private List<BattleParticipant> getAliveBattleParticipants(Battle battle) {
        List<BattleParticipant> participants = battleParticipantRepository.findActiveParticipants(battle.getId());
        return participants;
    }

    private List<String> getStepOrder(BattleParticipant player, BattleParticipant computer) {
        List<String> stepOrder;
        if (player.getInitiative() > computer.getInitiative()) {
            stepOrder = Arrays.asList("attack", "damage", "defense");
        } else {
            stepOrder = Arrays.asList("defense", "attack", "damage");
        }

        return stepOrder;
    }

    private void updateNextStep(Battle battle, BattleParticipant player, BattleParticipant computer) {
        String currentStep = battle.getNextStep();
        if (currentStep == null || currentStep.isEmpty() || currentStep.isBlank()) {
            battle.setNextStep("initiative");
            battleRepository.save(battle);
            return;
        }

        List<String> steps = getStepOrder(player, computer);

        int currentIndex = steps.indexOf(currentStep);

        if (currentIndex == -1 || currentIndex == steps.size() - 1) {
            battle.setNextStep(steps.get(0));

            if (currentIndex == steps.size() -1) {
                updateBattleTurn(battle);
            }
        }

        else {
            battle.setNextStep(steps.get(currentIndex + 1));
        }

        battleRepository.save(battle);
    }

    private void endGame(Battle battle) {
        battle.setIsActive(false);
    }

    private void updateBattleTurn(Battle battle) {
        battle.setTurn(battle.getTurn()+1);
        battleRepository.save(battle);
    }

    public BattleParticipant getCharacter(List<BattleParticipant> entries, Boolean playerCharacter) {
        return entries.stream()
                      .filter(entry -> entry.getPlayerCharacter().equals(playerCharacter))
                      .findFirst()
                      .orElse(null);
    }
    
    public BattleDTO runBattleStep(UUID battle_id, String operation){
        BattleDTO resultMessage;
        Optional<Battle> battleOptional = battleRepository.findById(battle_id);
        Battle battle = battleOptional.orElseThrow(() -> new RuntimeException("Batalha não encontrada para o ID: " + battle_id));
    
        if (!battle.getIsActive()) {
            resultMessage = new BattleDTO.Builder()
                        .message("Essa batalha já foi finalizada.")
                        .build();
            return resultMessage;
        }

        String nextStep = battle.getNextStep();
        if (!operation.equals(nextStep)) {
    
            switch(nextStep) {
                case "attack":
                    resultMessage = new BattleDTO.Builder()
                        .message("Você deve atacar agora.")
                        .build();
                    return resultMessage;
    
                case "defense":
                    resultMessage = new BattleDTO.Builder()
                        .message("Você deve se defender agora.")
                        .build();
                    return resultMessage;
    
                case "damage":
                    resultMessage = new BattleDTO.Builder()
                        .message("Você deve realizar a rolagem de dano.")
                        .build();
                    return resultMessage;
    
                case "initiative":
                    resultMessage = new BattleDTO.Builder()
                        .message("Você deve rolar a iniciativa primeiro.")
                        .build();
                    return resultMessage;
    
                default:
                    resultMessage = new BattleDTO.Builder()
                        .message("Algo deu errado.")
                        .build();
                    return resultMessage;
            }
        }
    
        List<BattleParticipant> participants = getAliveBattleParticipants(battle);
        BattleParticipant player = getCharacter(participants, true);
        BattleParticipant computer = getCharacter(participants, false);
    
        if ("attack".equals(operation)) {
            RollResultDTO attack = rollAttack(player);
            RollResultDTO defense = rollDefense(computer);
    
            String attackMessage = "O " + player.getCharacter().getName() + " se prepara para atacar... ";
            Boolean attackHasHit = checkIfAttackHit(attack, defense);
    
            resultMessage = new BattleDTO.Builder()
                .turn(battle.getTurn())
                .message(attackMessage + (attackHasHit ? "E acerta!" : "Mas erra."))
                .playerRoll(attack.getRolls())
                .playerRollMessage(attack.getMessage())
                .computerRoll(defense.getRolls())
                .computerRollMessage(defense.getMessage())
                .playerCharacter(player.getCharacter().getName())
                .computerCharacter(computer.getCharacter().getName())
                .build();
    
            if (!attackHasHit) {              
                updateNextStep(battle, player, computer);
            }
        }
    
        else if ("damage".equals(operation)) {
            RollResultDTO damage = rollDamage(player);
            String damageMessage = "O " + player.getCharacter().getName() + " golpeia firme e causa " + damage.getTotal() + " pontos de dano em seu oponente.";
    
            resultMessage = new BattleDTO.Builder()
                .turn(battle.getTurn())
                .message(damageMessage)
                .playerRoll(damage.getRolls())
                .playerRollMessage(damage.getMessage())
                .playerCharacter(player.getCharacter().getName())
                .computerCharacter(computer.getCharacter().getName())
                .build();
    
            takeDamage(computer, damage);
        }
    
        else if ("defense".equals(operation)) {
            RollResultDTO attack = rollAttack(computer);
            RollResultDTO defense = rollDefense(player);
    
            String attackMessage = "O " + computer.getCharacter().getName() + " se prepara para atacar... ";
            Boolean attackHasHit = checkIfAttackHit(attack, defense);
    
            resultMessage = new BattleDTO.Builder()
                .turn(battle.getTurn())
                .message(attackMessage + (attackHasHit ? "E acerta!" : "Mas erra!"))
                .playerRoll(defense.getRolls())
                .playerRollMessage(defense.getMessage())
                .computerRoll(attack.getRolls())
                .computerRollMessage(attack.getMessage())
                .playerCharacter(player.getCharacter().getName())
                .computerCharacter(computer.getCharacter().getName())
                .build();
    
            if (attackHasHit) {
                RollResultDTO damage = rollDamage(computer);
                String damageMessage = "O " + player.getCharacter().getName() + " golpeia firme e causa " + damage.getTotal() + " pontos de dano em seu oponente.";
                BattleDTO damageDTO = new BattleDTO.Builder()
                    .message(damageMessage)
                    .playerRoll(damage.getRolls())
                    .playerRollMessage(damage.getMessage())
                    .build();
    
                resultMessage.merge(damageDTO);
                takeDamage(player, damage);
            }
        }
    
        else {
            battle.setTurn(1);
            battleRepository.save(battle);
            resultMessage = settleInitiatives(player, computer);
        }
    
        if (player.getHp() <= 0) {
            endGame(battle);
            resultMessage.setMessage(resultMessage.getMessage() + "\nA batalha acabou. Você foi derrotado.");
        }
    
        else if (computer.getHp() <= 0) {
            endGame(battle);
            resultMessage.setMessage(resultMessage.getMessage() + "\nA batalha acabou. Você venceu!");
        }
    
        battleService.logBattleStep(battle, player, computer, resultMessage);
        updateNextStep(battle, player, computer);

        return resultMessage;
    }

}
