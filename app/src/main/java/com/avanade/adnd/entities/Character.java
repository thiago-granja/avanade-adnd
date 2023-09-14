package com.avanade.adnd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
@Entity
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome deve ser preenchido")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "O tipo deve ser preenchido")
    @Pattern(regexp = "^(hero|monster)$", message = "O tipo deve ser 'hero' ou 'monster'")
    private String type;

    @NotNull(message = "HP deve ser preenchido")
    @Min(value = 1, message = "HP deve ser maior que 0")
    @Max(value = 30, message = "HP não pode exceder 30")
    private Integer hp;

    @NotNull(message = "Força deve ser preenchido")
    @Min(value = 1, message = "Força deve ser maior que 0")
    @Max(value = 30, message = "Força não pode exceder 30")
    private Integer strength;

    @NotNull(message = "Defesa deve ser preenchido")
    @Min(value = 1, message = "Defesa deve ser maior que 0")
    @Max(value = 30, message = "Defesa não pode exceder 30")
    private Integer defense;

    @NotNull(message = "Agilidade deve ser preenchido")
    @Min(value = 1, message = "Agilidade deve ser maior que 0")
    @Max(value = 30, message = "Agilidade não pode exceder 30")
    private Integer agility;

    @NotNull(message = "Quantidade de dados deve ser preenchido")
    @Min(value = 1, message = "Quantidade de dados deve ser maior que 0")
    @Max(value = 10, message = "Quantidade de dados não pode exceder 10")
    private Integer diceQuantity;

    @NotNull(message = "Faces do dado deve ser preenchido")
    @Min(value = 1, message = "Faces do dado deve ser maior que 0")
    @Max(value = 20, message = "Faces do dado não pode exceder 20")
    private Integer diceFaces;
}
