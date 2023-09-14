package com.avanade.adnd.dtos;

import java.util.List;

import lombok.Data;

@Data
public class RollResultDTO {
    private List<Integer> rolls;
    private Integer total;
    private String message;
    
    public RollResultDTO(List<Integer> rolls, Integer total, String message) {
        this.rolls = rolls;
        this.total = total;
        this.message = message;
    }
}
