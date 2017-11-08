package com.example.trw.sampleroompersistence.ui.award;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.db.callback.SendDataCallback;
import com.example.trw.sampleroompersistence.db.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.db.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.ui.adapter.PlayerItemConverter;
import com.example.trw.sampleroompersistence.ui.adapter.PlayerAdapter;
import com.example.trw.sampleroompersistence.db.dao.InsertData;
import com.example.trw.sampleroompersistence.db.dao.QueryData;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;
import com.example.trw.sampleroompersistence.db.entity.AwardEntity;
import com.example.trw.sampleroompersistence.db.entity.PlayerContract;
import com.example.trw.sampleroompersistence.db.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.db.entity.ProfileEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AwardFragment extends Fragment implements View.OnClickListener
        , SendListDataCallback, SendStatusCallback, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = AwardFragment.class.getSimpleName();

    public static final String AWARD_FIFA_FIFPRO_WORLD_XI = "FIFA FIFPro World XI";
    public static final String AWARD_FIFA_BALLON_D_OR = "FIFA Ballon d'Or";

    private EditText editTextPlayerId;
    private EditText editTextAwardSeason;
    private RadioGroup radioGroupAwards;
    private Button buttonVote;
    private TextView textViewPlayerName;
    private Spinner spinnerPlayerName;
    private RecyclerView recyclerViewPlayer;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PlayerAdapter playerAdapter;

    private int awardSeason;
    private List<ProfileEntity> listPlayer;
    private List<PlayerWithAward> listPlayerAward;

    public static AwardFragment newInstance() {
        return new AwardFragment();
    }

    public AwardFragment() {
        awardSeason = Calendar.getInstance().get(Calendar.YEAR) - 1;
        listPlayer = new ArrayList<>();
        listPlayerAward = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_award, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextPlayerId = view.findViewById(R.id.et_player_id);
        editTextAwardSeason = view.findViewById(R.id.et_award_season);
        radioGroupAwards = view.findViewById(R.id.rg_awards);
        buttonVote = view.findViewById(R.id.btn_vote);
        textViewPlayerName = view.findViewById(R.id.tv_player_id);
        spinnerPlayerName = view.findViewById(R.id.spinner_player_name);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_player_award);
        recyclerViewPlayer = view.findViewById(R.id.rv_player_with_award);
        recyclerViewPlayer.setLayoutManager(new LinearLayoutManager(getContext()));
        editTextAwardSeason.setText(getString(R.string.season_year, awardSeason));

        buttonVote.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FifaDatabase.destroyInstance();
    }

    private void setAdapterData(List<PlayerWithAward> listPlayerAward) {
        playerAdapter = new PlayerAdapter();
        playerAdapter.setItemList(PlayerItemConverter.createPlayerAwardItem(listPlayerAward));
        recyclerViewPlayer.setAdapter(playerAdapter);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            QueryData.onLoadProfile(getContext(), this);
            QueryData.onLoadPlayerWithAward(getContext(), this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == buttonVote) {
            votePlayer();
        }
    }

    public void votePlayer() {
        String votePlayerId = editTextPlayerId.getText().toString();
        int awardButtonId = radioGroupAwards.getCheckedRadioButtonId();
        if (isVotePlayerIdInvalid(votePlayerId, awardButtonId)) {
            showVotePlayerIdInvalid();
        } else {
            insertVotePlayerAward(votePlayerId,
                    getAwardByRadioButtonId(awardButtonId),
                    awardSeason);
        }
    }

    private boolean isVotePlayerIdInvalid(String playerId, int awardButtonId) {
        return playerId.isEmpty()
                || awardButtonId == -1;
    }

    private void showVotePlayerIdInvalid() {
        Toast.makeText(getContext()
                , R.string.error_form_invalid
                , Toast.LENGTH_SHORT)
                .show();
    }

    private void insertVotePlayerAward(String votePlayerId, String award, int awardSeason) {
        int playerId = Integer.parseInt(votePlayerId);
        AwardEntity awardEntity = new AwardEntity();
        awardEntity.setPlayerId(playerId);
        awardEntity.setFifaAward(award);
        awardEntity.setFifaAwardSeason(awardSeason);
        InsertData.onInsertAward(getContext(), awardEntity, this);
    }

    @Override
    public void loadProfileDataCallback(List<ProfileEntity> profileEntity, boolean isSuccess) {
        if (isSuccess) {
            listPlayer = profileEntity;
            setSpinnerAdapter();
        } else {
            swipeRefreshLayout.setRefreshing(false);
            recyclerViewPlayer.removeAllViewsInLayout();
        }
    }

    private void setSpinnerAdapter() {
        List<String> listPlayerName = new ArrayList<>();
        for (ProfileEntity player : listPlayer) {
            listPlayerName.add(player.getPlayerName());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listPlayerName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayerName.setAdapter(spinnerAdapter);
        spinnerPlayerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String playerName = parent.getItemAtPosition(position).toString();
                QueryData.onFindPlayerIdWithPlayerName(getContext(), playerName, new SendDataCallback() {
                    @Override
                    public void findPlayerIdCallback(int playerId, boolean status) {
                        editTextPlayerId.setText(String.valueOf(playerId));
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void loadPlayerWithAwardDataCallback(List<PlayerWithAward> playerWithAwards, boolean isSuccess) {
        if (isSuccess) {
            swipeRefreshLayout.setRefreshing(false);
            listPlayerAward = playerWithAwards;
            setAdapterData(listPlayerAward);
        }
    }

    @Override
    public void loadPlayerContractCallback(List<PlayerContract> playerContract, boolean isSuccess) {

    }

    @Override
    public void checkStatusCallback(boolean isSuccess) {
        if (isSuccess) {
            Toast.makeText(getContext()
                    , R.string.status_vote_successful
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        QueryData.onLoadPlayerWithAward(getContext(), this);
        Toast.makeText(getContext()
                , R.string.status_update_successful
                , Toast.LENGTH_SHORT)
                .show();
    }

    private String getAwardByRadioButtonId(int AwardRadioButtonId) {
        switch (AwardRadioButtonId) {
            case R.id.rb_fifpro:
                return AWARD_FIFA_FIFPRO_WORLD_XI;
            case R.id.rb_ballondor:
                return AWARD_FIFA_BALLON_D_OR;
        }
        return null;
    }
}
