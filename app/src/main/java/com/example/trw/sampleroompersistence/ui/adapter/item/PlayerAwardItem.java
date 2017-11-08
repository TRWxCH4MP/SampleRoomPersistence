package com.example.trw.sampleroompersistence.ui.adapter.item;

import com.example.trw.sampleroompersistence.ui.adapter.ViewType;

/**
 * Created by TRW on 2/10/2560.
 */

public class PlayerAwardItem extends BaseItem {

    private int num;
    private String playerName;
    private String playerPosition;
    private String playerClub;
    private String fifaAward;
    private int fifaAwardSeason;

    public PlayerAwardItem() {
        super(ViewType.TYPE_LIST_PLAYER_AWARD);
    }

    public int getNum() {
        return num;
    }

    public PlayerAwardItem setNum(int num) {
        this.num = num;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerAwardItem setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public PlayerAwardItem setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
        return this;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public PlayerAwardItem setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
        return this;
    }

    public String getFifaAward() {
        return fifaAward;
    }

    public PlayerAwardItem setFifaAward(String fifaAward) {
        this.fifaAward = fifaAward;
        return this;
    }

    public int getFifaAwardSeason() {
        return fifaAwardSeason;
    }

    public PlayerAwardItem setFifaAwardSeason(int fifaAwardSeason) {
        this.fifaAwardSeason = fifaAwardSeason;
        return this;
    }
}
