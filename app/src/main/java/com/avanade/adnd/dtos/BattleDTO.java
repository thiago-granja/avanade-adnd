package com.avanade.adnd.dtos;

import java.util.List;

import lombok.Data;

@Data
public class BattleDTO {
    private String player_character;
    private String computer_character;
    private List<Integer> roll;
    private String roll_message;
    private String message;
    private Integer turn;

    private BattleDTO(Builder builder) {
        this.player_character = builder.player_character;
        this.computer_character = builder.computer_character;
        this.roll = builder.roll;
        this.roll_message = builder.roll_message;
        this.message = builder.message;
        this.turn = builder.turn;
    }

    public static class Builder {
        private String player_character;
        private String computer_character;
        private List<Integer> roll;
        private String roll_message;
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

        public Builder roll(List<Integer> roll) {
            this.roll = roll;
            return this;
        }

        public Builder rollMessage(String roll_message) {
            this.roll_message = roll_message;
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
        if(other.getRoll() != null) {
            if(this.roll != null) {
                this.roll.addAll(other.getRoll());
            } else {
                this.roll = other.getRoll();
            }
        }
        
        if(other.getRoll_message() != null) {
            this.roll_message = (this.roll_message != null ? this.roll_message : "") + other.getRoll_message();
        }
        
        if(other.getMessage() != null) {
            this.message = (this.message != null ? this.message : "") + other.getMessage();
        }

        if(other.getTurn() != null) {
            this.turn = other.getTurn();
        }
    }
}
