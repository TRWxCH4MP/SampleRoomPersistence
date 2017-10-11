package com.example.trw.sampleroompersistence.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.PlayerContract;
import com.example.trw.sampleroompersistence.room.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

@Dao
public interface FifaDao {

    @Insert
    void insertProfile(ProfileEntity profileEntity);

    @Insert
    void insertAward(AwardEntity awardEntity);

    @Query("SELECT * FROM profile")
    List<ProfileEntity> loadAllProfile();

    @Query("SELECT player_id FROM profile WHERE player_name LIKE :playerName ")
    int findPlayerIdWithPlayerName(String playerName);

    @Query("SELECT profile.player_name AS playerName" +
            ", profile.player_position AS playerPosition" +
            ", profile.player_club AS playerClub" +
            ", award.fifa_award AS fifaAward" +
            ", award.fifa_award_season AS fifaAwardSeason" +
            " FROM profile, award" +
            " WHERE profile.player_id = award.player_id")
    List<PlayerWithAward> loadPlayerWithAward();

    @Query("SELECT player_id AS playerId" +
            ", player_name AS playerName" +
            ", player_club AS playerClub" +
            ", player_contract_commence AS playerContractCommence" +
            ", player_contract_exp AS playerContractExp" +
            ", player_contract_exp-player_contract_commence AS ContractExp" +
            " FROM profile")
    List<PlayerContract> loadAllContract();

    @Query("UPDATE profile " +
            " SET player_contract_commence = :playerContractCommence" +
            ", player_contract_exp = :playerContractExp" +
            " WHERE player_id = :playerId")
    void updatePlayerContract(int playerId, int playerContractCommence, int playerContractExp);

    @Query("DELETE FROM profile WHERE player_id = :playerId")
    void deletePlayerByPlayerId(int playerId);

    @Query("DELETE FROM profile")
    void deleteAllPlayer();

    @Query("DELETE FROM award")
    void deleteAllAward();

}
