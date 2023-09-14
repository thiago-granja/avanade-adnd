package com.avanade.adnd.services;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class DiceRollerService {

    private static final short MIN_DIE_FACES = 1;
    private static final short MAX_DIE_FACES = 20;
    private static final short MIN_DIE_COUNT = 1;
    private static final short MAX_DIE_COUNT = 10;

    private final Random random = new Random();

    public Integer[] rollDice(Integer dice, Integer faces) throws IllegalArgumentException {
        if (dice < MIN_DIE_COUNT || dice > MAX_DIE_COUNT) {
            throw new IllegalArgumentException("Você deve jogar de 1 a 10 dados");
        }
        if (faces < MIN_DIE_FACES || faces > MAX_DIE_FACES) {
            throw new IllegalArgumentException("Os dados devem ter de 1 a 20 lados");
        }

        Integer[] rolls = new Integer[dice];
        for (int i = 0; i < dice; i++) {
            rolls[i] = (1 + random.nextInt(faces));
        }
        return rolls;
    }
}
