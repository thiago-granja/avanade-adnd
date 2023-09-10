package com.avanade.adnd.services;

import com.avanade.adnd.entities.BattleParticipant;
import com.avanade.adnd.repositories.BattleParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattleParticipantService {

    @Autowired
    private BattleParticipantRepository battleParticipantRepository;

    public BattleParticipant addParticipant(BattleParticipant participant) {
        return battleParticipantRepository.save(participant);
    }
}
