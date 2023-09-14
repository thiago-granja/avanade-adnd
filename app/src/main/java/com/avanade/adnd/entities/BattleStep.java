package com.avanade.adnd.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battle_participant_player_id")
    private BattleParticipant player;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "battle_participant_computer_id")
    private BattleParticipant computer;

    private List<Integer> playerRoll;
    private String playerRollMessage;
    private List<Integer> computerRoll;
    private String computerRollMessage;
    private String message;
    private Integer turn;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
