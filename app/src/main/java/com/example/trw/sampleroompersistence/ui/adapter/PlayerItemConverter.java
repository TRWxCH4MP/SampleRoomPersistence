package com.example.trw.sampleroompersistence.ui.adapter;

import com.example.trw.sampleroompersistence.ui.adapter.item.BaseItem;
import com.example.trw.sampleroompersistence.ui.adapter.item.PlayerDetailItem;
import com.example.trw.sampleroompersistence.ui.adapter.item.PlayerAwardItem;
import com.example.trw.sampleroompersistence.db.entity.PlayerContract;
import com.example.trw.sampleroompersistence.db.entity.PlayerWithAward;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

public class PlayerItemConverter {
    private static String TAG = PlayerItemConverter.class.getSimpleName();

    public static List<BaseItem> createPlayerAwardItem(List<PlayerWithAward> playerWithAwards) {
        List<BaseItem> itemList = new ArrayList<>();
        if (!playerWithAwards.isEmpty()) {
            for (int index = 0; index < playerWithAwards.size(); index++) {
                itemList.add(new PlayerAwardItem()
                        .setNum(index + 1)
                        .setPlayerName(playerWithAwards.get(index).getPlayerName())
                        .setPlayerPosition(playerWithAwards.get(index).getPlayerPosition())
                        .setPlayerClub(playerWithAwards.get(index).getPlayerClub())
                        .setFifaAward(playerWithAwards.get(index).getFifaAward())
                        .setFifaAwardSeason(playerWithAwards.get(index).getFifaAwardSeason())
                );
            }
        }
        return itemList;
    }

    public static List<BaseItem> createPlayerContractItem(List<PlayerContract> playerContract) {
        List<BaseItem> itemList = new ArrayList<>();
        if (!playerContract.isEmpty()) {
            for (int index = 0; index < playerContract.size(); index++) {
                itemList.add(new PlayerDetailItem()
                        .setNum(index + 1)
                        .setPlayerId(playerContract.get(index).getPlayerId())
                        .setPlayerName(playerContract.get(index).getPlayerName())
                        .setPlayerClub(playerContract.get(index).getPlayerClub())
                        .setContractExp(playerContract.get(index).getContractExp()));
            }
        }
        return itemList;
    }
}
