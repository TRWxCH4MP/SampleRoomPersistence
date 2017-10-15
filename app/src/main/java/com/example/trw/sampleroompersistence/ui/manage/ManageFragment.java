package com.example.trw.sampleroompersistence.ui.manage;


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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.db.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.db.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.ui.adapter.PlayerItemConverter;
import com.example.trw.sampleroompersistence.ui.adapter.PlayerAdapter;
import com.example.trw.sampleroompersistence.db.dao.DeleteData;
import com.example.trw.sampleroompersistence.db.dao.QueryData;
import com.example.trw.sampleroompersistence.db.dao.UpdateData;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;
import com.example.trw.sampleroompersistence.db.entity.PlayerContract;
import com.example.trw.sampleroompersistence.db.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.db.entity.ProfileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageFragment extends Fragment implements
        SendListDataCallback
        , View.OnClickListener
        , SendStatusCallback
        , SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = ManageFragment.class.getSimpleName();

    private EditText editTextCommence;
    private EditText editTextExp;
    private TextView textViewTo;
    private RecyclerView recyclerViewPlayer;
    private PlayerAdapter playerAdapter;
    private Spinner spinnerPlayerName;
    private Button buttonUpdate;
    private Button buttonDelete;
    private Button buttonDeleteAll;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int playerId;
    private int playerContractCommence;
    private int playerContractExp;

    private List<PlayerContract> listPlayerContract = new ArrayList<>();

    public static ManageFragment newInstance() {
        return new ManageFragment();
    }

    public ManageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextCommence = view.findViewById(R.id.et_contract_commence);
        editTextExp = view.findViewById(R.id.et_contract_exp);
        textViewTo = view.findViewById(R.id.tv_to);
        spinnerPlayerName = view.findViewById(R.id.spinner_player_name);
        buttonUpdate = view.findViewById(R.id.btn_update);
        buttonDelete = view.findViewById(R.id.btn_delete);
        buttonDeleteAll = view.findViewById(R.id.btn_delete_all);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_player_contract);
        recyclerViewPlayer = view.findViewById(R.id.rv_player);
        recyclerViewPlayer.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL
                , false));
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setAdapterData(List<PlayerContract> listPlayerContract) {
        playerAdapter = new PlayerAdapter();
        playerAdapter.setItemList(PlayerItemConverter.createPlayerContractItem(listPlayerContract));
        recyclerViewPlayer.setAdapter(playerAdapter);
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible) {
            QueryData.onLoadPlayerWithContract(getContext(), this);
        }
    }

    @Override
    public void loadProfileDataCallback(List<ProfileEntity> profileEntity, boolean status) {

    }

    @Override
    public void loadPlayerWithAwardDataCallback(List<PlayerWithAward> awardEntities, boolean status) {

    }

    @Override
    public void loadPlayerContractCallback(List<PlayerContract> playerContract, boolean status) {
        if (status) {
            swipeRefreshLayout.setRefreshing(false);
            listPlayerContract = playerContract;
            setAdapterData(listPlayerContract);
            setSpinnerAdapter();

        } else {
            swipeRefreshLayout.setRefreshing(false);
            recyclerViewPlayer.removeAllViewsInLayout();
        }
    }

    private void setSpinnerAdapter() {
        List<String> listPlayerName = new ArrayList<>();
        for (int index = 0; index < listPlayerContract.size(); index++) {
            listPlayerName.add(listPlayerContract.get(index).getPlayerName());
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, listPlayerName);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayerName.setAdapter(spinnerAdapter);
        spinnerPlayerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String playerName = parent.getItemAtPosition(position).toString();
                for (int index = 0; index < listPlayerContract.size(); index++) {
                    if (listPlayerContract.get(index).getPlayerName().equals(playerName)) {
                        playerId = Integer.parseInt(String.valueOf(listPlayerContract.get(index).getPlayerId()));
                        editTextCommence.setText(String.valueOf(listPlayerContract.get(index).getPlayerContractCommence()));
                        editTextExp.setText(String.valueOf(listPlayerContract.get(index).getPlayerContractExp()));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                playerContractCommence = Integer.parseInt(String.valueOf(editTextCommence.getText().toString().trim()));
                playerContractExp = Integer.parseInt(String.valueOf(editTextExp.getText().toString().trim()));
                UpdateData.onUpdateContract(getContext()
                        , playerId
                        , playerContractCommence
                        , playerContractExp
                        , this);
                break;
            case R.id.btn_delete:
                DeleteData.onDeletePlayerByPlayerId(getContext(), playerId, this);
                break;
            case R.id.btn_delete_all:
                DeleteData.onDeleteAllPlayer(getContext(), this);
        }
    }

    @Override
    public void checkStatusCallback(boolean status) {
        if (status) {
            Toast.makeText(getContext()
                    , "Successful"
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        QueryData.onLoadPlayerWithContract(getContext(), this);
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
