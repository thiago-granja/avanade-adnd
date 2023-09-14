package com.avanade.adnd.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class BattleParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battle_id")
    private Battle battle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "char_id")
    private Character character;
    
    private Integer hp;
    private Integer initiative;
    private Boolean playerCharacter;
    
    public BattleParticipant() {
    }
    
    public BattleParticipant(Character character, boolean isPlayerCharacter) {
        this.character = character;
        this.playerCharacter = isPlayerCharacter;
    }

}
