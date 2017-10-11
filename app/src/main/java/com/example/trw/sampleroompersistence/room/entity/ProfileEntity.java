package com.example.trw.sampleroompersistence.room.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by TRW on 1/10/2560.
 */

@Entity (tableName = "profile")
public class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id")
    private int playerId;

    @ColumnInfo(name = "player_name")
    private String playerName;

    @ColumnInfo(name = "player_position")
    private String playerPosition;

    @ColumnInfo(name = "player_nationality")
    private String playerNationality;

    @ColumnInfo(name = "player_club")
    private String playerClub;

    @ColumnInfo(name = "player_contract_commence")
    private int playerContractCommence;

    @ColumnInfo(name = "player_contract_exp")
    private int playerContractExp;

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

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public String getPlayerNationality() {
        return playerNationality;
    }

    public void setPlayerNationality(String playerNationality) {
        this.playerNationality = playerNationality;
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
}
