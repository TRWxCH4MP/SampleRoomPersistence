package com.example.trw.sampleroompersistence.room.entity;

/**
 * Created by TRW on 6/10/2560.
 */

public class PlayerContract {

    private int playerId;
    private String playerName;
    private String playerClub;
    private int playerContractCommence;
    private int playerContractExp;
    private int ContractExp;


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerClub() {
        return playerClub;
    }

    public void setPlayerClub(String playerClub) {
        this.playerClub = playerClub;
    }

    public int getPlayerContractCommence() {
        return playerContractCommence;
    }

    public void setPlayerContractCommence(int playerContractCommence) {
        this.playerContractCommence = playerContractCommence;
    }

    public int getPlayerContractExp() {
        return playerContractExp;
    }

    public void setPlayerContractExp(int playerContractExp) {
        this.playerContractExp = playerContractExp;
    }

    public int getContractExp() {
        return ContractExp;
    }

    public void setContractExp(int ContractExp) {
        this.ContractExp = ContractExp;
    }
}