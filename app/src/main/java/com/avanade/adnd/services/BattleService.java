package com.avanade.adnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.avanade.adnd.dtos.BattleDTO;
import com.avanade.adnd.entities.Battle;
import com.avanade.adnd.entities.BattleLog;
import com.avanade.adnd.entities.BattleParticipant;
import com.avanade.adnd.repositories.BattleLogRepository;
import com.avanade.adnd.repositories.BattleParticipantRepository;
import com.avanade.adnd.repositories.BattleRepository;
import com.avanade.adnd.entities.Character;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
   
@Service
public class BattleService {

    @Autowired
    private CharacterService characterService;
    
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private BattleParticipantRepository battleParticipantRepository;

    @Autowired
    private BattleLogRepository battleLogRepository;


    public BattleDTO generateBattle(BattleDTO battleDTO) {
        Battle newBattle = new Battle();
        newBattle.setTurn(1);
        newBattle.setNextStep("initiative");
        newBattle.setIsActive(true);
        battleRepository.save(newBattle);
        BattleParticipant computer;
        BattleParticipant player;

        try {
            Character playerCharacter = characterService
                .getCharacterByName(battleDTO.getPlayer_character())
                .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado: " + battleDTO.getPlayer_character()));
            
            player = new BattleParticipant(playerCharacter, true);
            player.setBattle(newBattle);
            player.setHp(playerCharacter.getHp());
            battleParticipantRepository.save(player);

            if (battleDTO.getComputer_character() != null) {
                Character computerCharacter = characterService.getCharacterByName(battleDTO.getComputer_character())
                    .orElseThrow(() -> new EntityNotFoundException("Personagem não encontrado: " + battleDTO.getComputer_character()));
            
                computer = new BattleParticipant(computerCharacter, false);
                computer.setBattle(newBattle);
                computer.setHp(computerCharacter.getHp());
                battleParticipantRepository.save(computer);
            
            } else {
                Optional<Character> optionalMonster = characterService.getRandomMonster();
                if (optionalMonster.isPresent()) {
                    computer = new BattleParticipant(optionalMonster.get(), false);
                    computer.setBattle(newBattle);
                    battleParticipantRepository.save(computer);
                } else {
                    throw new EntityNotFoundException("Nenhum monstro encontrado no banco de dados.");
                }
            }

            BattleDTO resultDTO = new BattleDTO.Builder()
                        .playerCharacter(player.getCharacter().getName())
                        .computerCharacter(computer.getCharacter().getName())
                        .message("Batalha criada com sucesso! Utilize o ID " + newBattle.getId() + " para acessá-la.")
                        .build();

            return resultDTO;
        } catch (Exception e) {
            newBattle.setIsActive(false);
            battleRepository.save(newBattle);
            throw e;
        }
    }

    public List<Battle> getAllActiveBattles() {
        return battleRepository.findByIsActiveTrue();
    }

    public Optional<Battle> getBattleById(UUID id) {
        return battleRepository.findById(id);
    }

    void logBattleStep(Battle battle, BattleParticipant player, BattleParticipant computer, BattleDTO resultMessage) {
        BattleLog battleLog = new BattleLog();
        battleLog.setBattle(battle);
        battleLog.setPlayer(player);
        battleLog.setComputer(computer);
        battleLog.setTurn(battle.getTurn());
        battleLog.setPlayerRoll(resultMessage.getPlayerRoll());
        battleLog.setPlayerRollMessage(resultMessage.getPlayerRollMessage());
        battleLog.setComputerRoll(resultMessage.getComputerRoll());
        battleLog.setComputerRollMessage(resultMessage.getComputerRollMessage());
        battleLog.setMessage(resultMessage.getMessage());
    
        battleLogRepository.save(battleLog);
    }

    public ResponseEntity<Object> getLogsForBattle(UUID battleId) {
        Battle battle = battleRepository.findById(battleId).orElse(null);

        if (battle == null) {
            return new ResponseEntity<>("A batalha não foi encontrada.", HttpStatus.NOT_FOUND);
        }

        List<BattleLog> logs = battleLogRepository.findByBattleOrderByCreatedAtAsc(battle);

        if (logs.isEmpty()) {
            return new ResponseEntity<>("Ainda não existem registros dessa batalha.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(logs, HttpStatus.OK);
    }

    
    
}
