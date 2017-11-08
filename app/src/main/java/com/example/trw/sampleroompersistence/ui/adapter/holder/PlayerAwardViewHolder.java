package com.example.trw.sampleroompersistence.ui.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.example.trw.sampleroompersistence.R;

/**
 * Created by TRW on 2/10/2560.
 */

public class PlayerAwardViewHolder extends BaseViewHolder {
    private TextView textViewNumber;
    private TextView textViewPlayerName;
    private TextView textViewPlayerPosition;
    private TextView textViewPlayerClub;
    private TextView textViewAward;
    private TextView textViewAwardSeason;

    public PlayerAwardViewHolder(View itemView) {
        super(itemView);
        textViewNumber = itemView.findViewById(R.id.tv_number);
        textViewPlayerName = itemView.findViewById(R.id.tv_player_name);
        textViewPlayerPosition = itemView.findViewById(R.id.tv_player_position);
        textViewPlayerClub = itemView.findViewById(R.id.tv_player_club);
        textViewAward = itemView.findViewById(R.id.tv_award);
        textViewAwardSeason = itemView.findViewById(R.id.tv_award_season);
    }

    public void setNumber(int number) {
        textViewNumber.setText(String.valueOf(number));
    }

    public void setPlayerName(String playerName) {
        textViewPlayerName.setText(playerName);
    }

    public void setPlayerPosition(String playerPosition) {
        textViewPlayerPosition.setText(playerPosition);
    }

    public void setPlayerClub(String playerClub) {
        textViewPlayerClub.setText(playerClub);
    }

    public void setAward(String award) {
        textViewAward.setText(award);
    }

    public void setAwardSeason(int awardSeason) {
        textViewAwardSeason.setText(String.valueOf(awardSeason));
    }
}
