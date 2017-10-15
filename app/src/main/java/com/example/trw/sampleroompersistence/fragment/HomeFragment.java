package com.example.trw.sampleroompersistence.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.room.dao.InsertData;
import com.example.trw.sampleroompersistence.room.database.FifaDatabase;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, SendStatusCallback {
    private static final String TAG = HomeFragment.class.getSimpleName();

    private EditText editTextPlayerName;
    private EditText editTextPlayerNationality;
    private EditText editTextPlayerClub;
    private EditText editTextPlayerContractCommence;
    private EditText editTextPlayerContractExp;
    private RadioGroup radioGroupPosition;
    private Button buttonRegister;

    private String playerPosition;
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editTextPlayerName = view.findViewById(R.id.et_player_name);
        editTextPlayerNationality = view.findViewById(R.id.et_player_nationality);
        editTextPlayerClub = view.findViewById(R.id.et_player_club);
        editTextPlayerContractCommence = view.findViewById(R.id.et_player_contract_commence);
        editTextPlayerContractExp = view.findViewById(R.id.et_player_contract_exp);
        radioGroupPosition = view.findViewById(R.id.rg_position);
        buttonRegister = view.findViewById(R.id.btn_register);

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
