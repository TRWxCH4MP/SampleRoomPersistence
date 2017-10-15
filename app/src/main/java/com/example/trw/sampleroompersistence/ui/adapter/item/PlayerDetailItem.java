package com.example.trw.sampleroompersistence.ui.adapter.item;

import com.example.trw.sampleroompersistence.ui.adapter.ViewType;

/**
 * Created by TRW on 2/10/2560.
 */

public class PlayerDetailItem extends BaseItem {

    private int num;
    private int playerId;
    private String playerName;
    private String playerPosition;
    private String playerNationality;
    private String playerClub;
    private int playerContractCommence;
    private int playerContractExp;
    private int contractExp;
    public PlayerDetailItem() {
        super(ViewType.TYPE_LIST_PLAYER_CONTRACT);
    }

    public int getNum() {
        return num;
    }

    public PlayerDetailItem setNum(int num) {
        this.num = num;
        return this;
    }

    public int getPlayerId() {
        return playerId;
    }

    public PlayerDetailItem setPlayerId(int playerId) {
        this.playerId = playerId;
        return this;
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerDetailItem setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public PlayerDetailItem setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
        return this;
    }

    public String getPlayerNationality() {
        return playerNationality;
    }

    public PlayerDetailItem setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality;
        return this;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public PlayerDetailItem setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
        return this;
    }

    public int getPlayerContractCommence() {
        return playerContractCommence;
    }

    public PlayerDetailItem setPlayerContractCommence(int playerContractCommence) {
        this.playerContractCommence = playerContractCommence;
        return this;
    }

    public int getPlayerContractExp() {
        return playerContractExp;
    }

    public PlayerDetailItem setPlayerContractExp(int playerContractExp) {
        this.playerContractExp = playerContractExp;
        return this;
    }

    public int getContractExp() {
        return contractExp;
    }

    public PlayerDetailItem setContractExp(int contractExp) {
        this.contractExp = contractExp;
        return this;
    }
}
