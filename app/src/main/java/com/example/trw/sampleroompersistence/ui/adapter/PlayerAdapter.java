package com.example.trw.sampleroompersistence.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.ui.adapter.holder.BaseViewHolder;
import com.example.trw.sampleroompersistence.ui.adapter.holder.PlayerAwardViewHolder;
import com.example.trw.sampleroompersistence.ui.adapter.holder.PlayerDetailViewHolder;
import com.example.trw.sampleroompersistence.ui.adapter.item.BaseItem;
import com.example.trw.sampleroompersistence.ui.adapter.item.PlayerAwardItem;
import com.example.trw.sampleroompersistence.ui.adapter.item.PlayerDetailItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

public class PlayerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<BaseItem> itemList = new ArrayList<>();

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ViewType.TYPE_LIST_PLAYER_CONTRACT) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_player_contract, parent, false);
            return new PlayerDetailViewHolder(view);
        } else if (viewType == ViewType.TYPE_LIST_PLAYER_AWARD) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_player_award, parent, false);
            return new PlayerAwardViewHolder(view);
        }
        throw new RuntimeException("type is not match");
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        BaseItem item = itemList.get(position);
        if (holder instanceof PlayerDetailViewHolder) {
            setUpPlayerDetail((PlayerDetailViewHolder) holder, (PlayerDetailItem) item);
        } else if (holder instanceof PlayerAwardViewHolder) {
            setUpPlayerAward((PlayerAwardViewHolder) holder, (PlayerAwardItem) item);
        }
    }

    @Override
    public int getItemCount() {
        if (!itemList.isEmpty() || itemList != null) {
            return itemList.size();
        } else {
            for (int i = 0; i < itemList.size(); i++) {
                this.itemList.remove(0);
            }
            this.notifyItemRangeRemoved(0, itemList.size());
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position).getType();
    }

    public void setItemList(List<BaseItem> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    private void setUpPlayerDetail(PlayerDetailViewHolder viewHolder, PlayerDetailItem item) {
        viewHolder.setNumber(item.getNum());
        viewHolder.setId(item.getPlayerId());
        viewHolder.setName(item.getPlayerName());
        viewHolder.setClub(item.getPlayerClub());
        viewHolder.setContract(item.getContractExp());
    }

    private void setUpPlayerAward(PlayerAwardViewHolder viewHolder, PlayerAwardItem item) {
        viewHolder.setNumber(item.getNum());
        viewHolder.setPlayerName(item.getPlayerName());
        viewHolder.setPlayerPosition(item.getPlayerPosition());
        viewHolder.setPlayerClub(item.getPlayerClub());
        viewHolder.setAward(item.getFifaAward());
        viewHolder.setAwardSeason(item.getFifaAwardSeason());
    }
}
