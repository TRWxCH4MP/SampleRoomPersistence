package com.example.trw.sampleroompersistence.fragment;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.trw.sampleroompersistence.callback.SendDataCallback;
import com.example.trw.sampleroompersistence.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.recyclerview.CreateItems;
import com.example.trw.sampleroompersistence.recyclerview.MainAdapter;
import com.example.trw.sampleroompersistence.room.dao.InsertData;
import com.example.trw.sampleroompersistence.room.dao.QueryData;
import com.example.trw.sampleroompersistence.room.database.FifaDatabase;
import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.PlayerContract;
import com.example.trw.sampleroompersistence.room.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AwardFragment extends Fragment implements View.OnClickListener
        , SendListDataCallback, SendStatusCallback, SwipeRefreshLayout.OnRefreshListener {

    String TAG = "AwardFragment";
    EditText editTextPlayerId;
    EditText editTextAwardSeason;
    RadioGroup radioGroupAwards;
    Button buttonVote;
    TextView textViewPlayerName;
    Spinner spinnerPlayerName;
    RecyclerView recyclerViewPlayer;
    MainAdapter mainAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    String award;
    int awardSeason = Calendar.getInstance().get(Calendar.YEAR) - 1;
    String inputPlayerId;
    List<ProfileEntity> listPlayer = new ArrayList<>();
    List<PlayerWithAward> listPlayerAward = new ArrayList<>();

    public AwardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_award, container, false);

        initializeUI(view);

        return view;
    }

    private void initializeUI(View view) {
        editTextPlayerId = (EditText) view.findViewById(R.id.et_player_id);
        editTextAwardSeason = (EditText) view.findViewById(R.id.et_award_season);
        radioGroupAwards = (RadioGroup) view.findViewById(R.id.rg_awards);
        buttonVote = (Button) view.findViewById(R.id.btn_vote);
        textViewPlayerName = (TextView) view.findViewById(R.id.tv_player_id);
        spinnerPlayerName = (Spinner) view.findViewById(R.id.spinner_player_name);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_player_award);
        recyclerViewPlayer = (RecyclerView) view.findViewById(R.id.rv_player_with_award);
        recyclerViewPlayer.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL
                , false));
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
