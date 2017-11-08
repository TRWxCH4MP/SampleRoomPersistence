package com.example.trw.sampleroompersistence.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by TRW on 2/10/2560.
 */

@Entity (tableName = "award")
public class AwardEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "award_id")
    private int awardId;

    @ColumnInfo(name = "player_id")
    private int playerId;

    @ColumnInfo(name = "fifa_award")
    private String fifaAward;

    @ColumnInfo(name = "fifa_award_season")
    private int fifaAwardSeason;

    public int getAwardId() {
        return awardId;
    }

    public void setAwardId(int awardId) {
        this.awardId = awardId;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getFifaAward() {
        return fifaAward;
    }

    public void setFifaAward(String fifaAward) {
        this.fifaAward = fifaAward;
    }

    public int getFifaAwardSeason() {
        return fifaAwardSeason;
    }

    public void setFifaAwardSeason(int fifaAwardSeason) {
        this.fifaAwardSeason = fifaAwardSeason;
    }
}
