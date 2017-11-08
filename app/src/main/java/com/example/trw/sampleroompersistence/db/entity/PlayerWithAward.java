package com.example.trw.sampleroompersistence.db.entity;

/**
 * Created by TRW on 6/10/2560.
 */

public class PlayerWithAward {

    private String playerName;
    private String playerPosition;
    private String playerClub;
    private String fifaAward;
    private int fifaAwardSeason;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public void setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
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
