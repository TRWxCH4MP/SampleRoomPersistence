package com.example.trw.sampleroompersistence.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.example.trw.sampleroompersistence.R;

/**
 * Created by TRW on 6/10/2560.
 */

public class PlayerDetailViewHolder extends BaseViewHolder {
    private TextView textViewId;
    private TextView textViewNumber;
    private TextView textViewName;
    private TextView textViewClub;
    private TextView textViewContract;

    public PlayerDetailViewHolder(View itemView) {
        super(itemView);

        textViewNumber = itemView.findViewById(R.id.tv_number);
        textViewId = itemView.findViewById(R.id.tv_player_id);
        textViewName = itemView.findViewById(R.id.tv_player_name);
        textViewClub = itemView.findViewById(R.id.tv_player_club);
        textViewContract = itemView.findViewById(R.id.tv_player_contract);
    }

    public void setNumber(int number) {
        textViewNumber.setText(String.valueOf(number));
    }

    public void setId(int playerId) {
        textViewId.setText(String.valueOf(playerId));
    }

    public void setName(String playerName) {
        textViewName.setText(playerName);
    }

    public void setClub(String playerClub) {
        textViewClub.setText(playerClub);
    }

    public void setContract(int contract) {
        textViewContract.setText(String.valueOf(contract));
    }

}
