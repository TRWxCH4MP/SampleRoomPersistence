package com.example.trw.sampleroompersistence.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.example.trw.sampleroompersistence.R;

/**
 * Created by TRW on 6/10/2560.
 */

public class PlayerDetailViewHolder extends BaseViewHolder {

    TextView textViewId;
    TextView textViewNum;
    TextView textViewName;
    TextView textViewClub;
    TextView textViewContract;

    public PlayerDetailViewHolder(View itemView) {
        super(itemView);

        textViewNum = (TextView) itemView.findViewById(R.id.tv_num);
        textViewId = (TextView) itemView.findViewById(R.id.tv_player_id);
        textViewName = (TextView) itemView.findViewById(R.id.tv_player_name);
        textViewClub = (TextView) itemView.findViewById(R.id.tv_player_club);
        textViewContract = (TextView) itemView.findViewById(R.id.tv_player_contract);
    }

    public void setNum(int number) {
        textViewNum.setText(String.valueOf(number));
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
