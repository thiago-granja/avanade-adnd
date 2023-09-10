package com.avanade.adnd.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class BattleStep {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;
    
    private Short turn;
    private Short turnStep;
    private String playerMessage;
    private String stepMessage;
    private Short diceRoll;
}
