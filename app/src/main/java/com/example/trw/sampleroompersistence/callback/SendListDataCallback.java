package com.example.trw.sampleroompersistence.callback;


import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.PlayerContract;
import com.example.trw.sampleroompersistence.room.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

public interface SendListDataCallback {
    void loadProfileDataCallback(List<ProfileEntity> profileEntity, boolean status);

    void loadPlayerWithAwardDataCallback(List<PlayerWithAward> awardEntities, boolean status);

    void loadPlayerContractCallback(List<PlayerContract> playerContract, boolean status);
}
