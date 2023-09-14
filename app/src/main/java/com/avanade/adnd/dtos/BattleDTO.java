package com.avanade.adnd.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BattleDTO {
    private String player_character;
    private String computer_character;
    private List<Integer> playerRoll;
    private String playerRollMessage;
    private List<Integer> computerRoll;
    private String computerRollMessage;
    private String message;
    private Integer turn;

    private BattleDTO(Builder builder) {
        this.player_character = builder.player_character;
        this.computer_character = builder.computer_character;
        this.playerRoll = builder.playerRoll;
        this.playerRollMessage = builder.playerRollMessage;
        this.computerRoll = builder.computerRoll;
        this.computerRollMessage = builder.computerRollMessage;
        this.message = builder.message;
        this.turn = builder.turn;
    }

    BattleDTO() {}

    public static class Builder {
        private String player_character;
        private String computer_character;
        private List<Integer> playerRoll;
        private String playerRollMessage;
        private List<Integer> computerRoll;
        private String computerRollMessage;
        private String message;
        private Integer turn;

        public Builder playerCharacter(String player_character) {
            this.player_character = player_character;
            return this;
        }

        public Builder computerCharacter(String computer_character) {
            this.computer_character = computer_character;
            return this;
        }

        public Builder playerRoll(List<Integer> playerRoll) {
            this.playerRoll = playerRoll;
            return this;
        }

        public Builder playerRollMessage(String playerRollMessage) {
            this.playerRollMessage = playerRollMessage;
            return this;
        }

        public Builder computerRoll(List<Integer> computerRoll) {
            this.computerRoll = computerRoll;
            return this;
        }

        public Builder computerRollMessage(String computerRollMessage) {
            this.computerRollMessage = computerRollMessage;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder turn(Integer turn) {
            this.turn = turn;
            return this;
        }

        public BattleDTO build() {
            return new BattleDTO(this);
        }
    }

    public void merge(BattleDTO other) {
        if (other.getPlayerRoll() != null) {
            if (this.playerRoll != null) {
                this.playerRoll = new ArrayList<>(this.playerRoll);
                this.playerRoll.addAll(other.getPlayerRoll());
            } else {
                this.playerRoll = new ArrayList<>(other.getPlayerRoll());
            }
        }

        if (other.getPlayerRollMessage() != null) {
            this.playerRollMessage = (this.playerRollMessage != null ? this.playerRollMessage : "") + other.getPlayerRollMessage();
        }

        if (other.getComputerRoll() != null) {
            if (this.computerRoll != null) {
                this.computerRoll = new ArrayList<>(this.computerRoll);
                this.computerRoll.addAll(other.getComputerRoll());
            } else {
                this.computerRoll = new ArrayList<>(other.getComputerRoll());
            }
        }

        if (other.getComputerRollMessage() != null) {
            this.computerRollMessage = (this.computerRollMessage != null ? this.computerRollMessage : "") + other.getComputerRollMessage();
        }

        if (other.getMessage() != null) {
            this.message = (this.message != null ? this.message : "") + other.getMessage();
        }

        if (other.getTurn() != null) {
            this.turn = other.getTurn();
        }
    }

}
