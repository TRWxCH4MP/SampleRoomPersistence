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
import com.example.trw.sampleroompersistence.ui.adapter.CreateItems;
import com.example.trw.sampleroompersistence.ui.adapter.MainAdapter;
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

    private EditText editTextPlayerId;
    private EditText editTextAwardSeason;
    private RadioGroup radioGroupAwards;
    private Button buttonVote;
    private TextView textViewPlayerName;
    private Spinner spinnerPlayerName;
    private RecyclerView recyclerViewPlayer;
    private MainAdapter mainAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String award;
    private int awardSeason = Calendar.getInstance().get(Calendar.YEAR) - 1;
    private String inputPlayerId;
    private List<ProfileEntity> listPlayer = new ArrayList<>();
    private List<PlayerWithAward> listPlayerAward = new ArrayList<>();

    public static AwardFragment newInstance() {
        return new AwardFragment();
    }

    public AwardFragment() {
        // Required empty public constructor
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
        editTextAwardSeason.setText("Season: " + awardSeason);

        radioGroupAwards.setOnClickListener(this);
        buttonVote.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setAdapterData(List<PlayerWithAward> listPlayerAward) {
        mainAdapter = new MainAdapter();
        mainAdapter.setItemList(CreateItems.createPlayerAwardItem(listPlayerAward));
        recyclerViewPlayer.setAdapter(mainAdapter);
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
        switch (radioGroupAwards.getCheckedRadioButtonId()) {
            case R.id.rb_fifpro:
                award = "FIFA FIFPro World XI";
                break;
            case R.id.rb_ballondor:
                award = "FIFA Ballon d'Or";
                break;
        }
        switch (v.getId()) {
            case R.id.btn_vote:
                inputPlayerId = editTextPlayerId.getText().toString();
                if (inputPlayerId.isEmpty()
                        || radioGroupAwards.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext()
                            , "Please complete the information !"
                            , Toast.LENGTH_SHORT)
                            .show();
                } else {
                    int playerId = Integer.parseInt(inputPlayerId);
                    AwardEntity awardEntity = new AwardEntity();
                    awardEntity.setPlayerId(playerId);
                    awardEntity.setFifaAward(award);
                    awardEntity.setFifaAwardSeason(awardSeason);
                    InsertData.onInsertAward(getContext(), awardEntity, this);
                }
                break;
        }
    }

    @Override
    public void loadProfileDataCallback(List<ProfileEntity> profileEntity, boolean status) {
        if (status) {
            listPlayer = profileEntity;
            setSpinnerAdapter();

        } else {
            swipeRefreshLayout.setRefreshing(false);
            recyclerViewPlayer.removeAllViewsInLayout();
        }
    }

    private void setSpinnerAdapter() {
        List<String> listPlayerName = new ArrayList<>();
        for (int index = 0; index < listPlayer.size(); index++) {
            listPlayerName.add(listPlayer.get(index).getPlayerName());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listPlayerName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayerName.setAdapter(spinnerAdapter);
        spinnerPlayerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String playerName = parent.getItemAtPosition(position).toString();

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
    public void loadPlayerWithAwardDataCallback(List<PlayerWithAward> playerWithAwards, boolean status) {
        if (status) {
            swipeRefreshLayout.setRefreshing(false);
            listPlayerAward = playerWithAwards;
            setAdapterData(listPlayerAward);
        }
    }

    @Override
    public void loadPlayerContractCallback(List<PlayerContract> playerContract, boolean status) {

    }

    @Override
    public void checkStatusCallback(boolean status) {
        if (status) {
            Toast.makeText(getContext()
                    , "Vote successful"
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        QueryData.onLoadPlayerWithAward(getContext(), this);
        Toast.makeText(getContext()
                , "Update successful"
                , Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FifaDatabase.destroyInstance();
    }
}
