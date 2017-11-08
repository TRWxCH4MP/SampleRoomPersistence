package com.example.trw.sampleroompersistence.db.callback;


import com.example.trw.sampleroompersistence.db.entity.PlayerContract;
import com.example.trw.sampleroompersistence.db.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.db.entity.ProfileEntity;

import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

public interface SendListDataCallback {
    void loadProfileDataCallback(List<ProfileEntity> profileEntity, boolean isSuccess);

    void loadPlayerWithAwardDataCallback(List<PlayerWithAward> awardEntities, boolean isSuccess);

    void loadPlayerContractCallback(List<PlayerContract> playerContract, boolean isSuccess);
}
