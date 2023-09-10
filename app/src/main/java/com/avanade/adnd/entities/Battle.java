package com.avanade.adnd.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Battle {
    @Id
    private UUID id = UUID.randomUUID();
    private String nextStep;
    private Boolean isActive;
}
