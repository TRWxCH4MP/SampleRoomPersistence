package com.example.trw.sampleroompersistence.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.recyclerview.CreateItems;
import com.example.trw.sampleroompersistence.recyclerview.MainAdapter;
import com.example.trw.sampleroompersistence.room.dao.InsertData;
import com.example.trw.sampleroompersistence.room.dao.QueryData;
import com.example.trw.sampleroompersistence.room.database.FifaDatabase;
import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, SendStatusCallback {

    String TAG = "main";
    EditText editTextPlayerName;
    EditText editTextPlayerNationality;
    EditText editTextPlayerClub;
    EditText editTextPlayerContractCommence;
    EditText editTextPlayerContractExp;
    RadioGroup radioGroupPosition;
    Button buttonRegister;

    String playerPosition;
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeUI(view);

        return view;
    }

    private void initializeUI(View view) {
        editTextPlayerName = (EditText) view.findViewById(R.id.et_player_name);
        editTextPlayerNationality = (EditText) view.findViewById(R.id.et_player_nationality);
        editTextPlayerClub = (EditText) view.findViewById(R.id.et_player_club);
        editTextPlayerContractCommence = (EditText) view.findViewById(R.id.et_player_contract_commence);
        editTextPlayerContractExp = (EditText) view.findViewById(R.id.et_player_contract_exp);
        radioGroupPosition = (RadioGroup) view.findViewById(R.id.rg_position);
        buttonRegister = (Button) view.findViewById(R.id.btn_register);

        editTextPlayerContractCommence.setText("Commence: " + currentYear);

        radioGroupPosition.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (radioGroupPosition.getCheckedRadioButtonId()) {
            case R.id.rb_fw:
                playerPosition = "Forward";
                break;
            case R.id.rb_mf:
                playerPosition = "Midfielder";
                break;
            case R.id.rb_df:
                playerPosition = "Defender";
                break;
            case R.id.rb_gk:
                playerPosition = "Goalkeeper";
                break;
        }

        switch (v.getId()) {
            case R.id.btn_register:
                String playerName = editTextPlayerName.getText().toString().trim();
                String playerNationality = editTextPlayerNationality.getText().toString().trim();
                String playerClub = editTextPlayerClub.getText().toString().trim();
                String playerContractCommence = editTextPlayerContractCommence.getText().toString().trim();
                String playerContractExp = editTextPlayerContractExp.getText().toString().trim();
                if (playerName.isEmpty()
                        || playerNationality.isEmpty()
                        || playerClub.isEmpty()
                        || playerContractCommence.isEmpty()
                        || playerContractExp.isEmpty()
                        || radioGroupPosition.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getContext()
                            , "Please complete the information !"
                            , Toast.LENGTH_SHORT)
                            .show();
                } else {
                    int contractExp = Integer.parseInt(playerContractExp);
                    ProfileEntity profile = new ProfileEntity();
                    profile.setPlayerName(playerName);
                    profile.setPlayerPosition(playerPosition);
                    profile.setPlayerNationality(playerNationality);
                    profile.setPlayerClub(playerClub);
                    profile.setPlayerContractCommence(currentYear);
                    profile.setPlayerContractExp(contractExp);
                    InsertData.onInsertProfile(getContext(), profile, this);
                }
                break;
        }
    }

    @Override
    public void checkStatusCallback(boolean status) {
        if (status) {
            editTextPlayerName.setText("");
            radioGroupPosition.clearCheck();
            editTextPlayerNationality.setText("");
            editTextPlayerClub.setText("");
            editTextPlayerContractExp.setText("");

            Toast.makeText(getContext()
                    , "Register successful"
                    , Toast.LENGTH_SHORT)
                    .show();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FifaDatabase.destroyInstance();
    }
}
