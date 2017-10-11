package com.example.trw.sampleroompersistence.fragment;


import android.content.Context;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.recyclerview.CreateItems;
import com.example.trw.sampleroompersistence.recyclerview.MainAdapter;
import com.example.trw.sampleroompersistence.room.dao.DeleteData;
import com.example.trw.sampleroompersistence.room.dao.QueryData;
import com.example.trw.sampleroompersistence.room.dao.UpdateData;
import com.example.trw.sampleroompersistence.room.database.FifaDatabase;
import com.example.trw.sampleroompersistence.room.entity.PlayerContract;
import com.example.trw.sampleroompersistence.room.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

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
    String TAG = "ManageFragment";
    EditText editTextCommence;
    EditText editTextExp;
    TextView textViewTo;
    RecyclerView recyclerViewPlayer;
    MainAdapter mainAdapter;
    Spinner spinnerPlayerName;
    Button buttonUpdate;
    Button buttonDelete;
    Button buttonDeleteAll;
    SwipeRefreshLayout swipeRefreshLayout;

    int playerId;
    int playerContractCommence;
    int playerContractExp;


    List<PlayerContract> listPlayerContract = new ArrayList<>();

    public ManageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_manage, container, false);

        initializeUI(view);

        return view;
    }

    private void initializeUI(View view) {
        editTextCommence = (EditText) view.findViewById(R.id.et_contract_commence);
        editTextExp = (EditText) view.findViewById(R.id.et_contract_exp);
        textViewTo = (TextView) view.findViewById(R.id.tv_to);
        spinnerPlayerName = (Spinner) view.findViewById(R.id.spinner_player_name);
        buttonUpdate = (Button) view.findViewById(R.id.btn_update);
        buttonDelete = (Button) view.findViewById(R.id.btn_delete);
        buttonDeleteAll = (Button) view.findViewById(R.id.btn_delete_all);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_player_contract);
        recyclerViewPlayer = (RecyclerView) view.findViewById(R.id.rv_player);
        recyclerViewPlayer.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL
                , false));

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonDeleteAll.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setAdapterData(List<PlayerContract> listPlayerContract) {
        mainAdapter = new MainAdapter();
        mainAdapter.setItemList(CreateItems.createPlayerContractItem(listPlayerContract));
        recyclerViewPlayer.setAdapter(mainAdapter);
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
        switch (v.getId()){
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
