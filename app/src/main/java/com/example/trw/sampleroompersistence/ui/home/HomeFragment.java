package com.example.trw.sampleroompersistence.ui.home;


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
import com.example.trw.sampleroompersistence.db.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.db.dao.InsertData;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;
import com.example.trw.sampleroompersistence.db.entity.ProfileEntity;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, SendStatusCallback {
    private static final String TAG = HomeFragment.class.getSimpleName();

    public static final String POSITION_FORWARD = "Forward";
    public static final String POSITION_MID_FIELDER = "Midfielder";
    public static final String POSITION_DEFENDER = "Defender";
    public static final String POSITION_GOAL_KEEPER = "Goalkeeper";

    private EditText editTextPlayerName;
    private EditText editTextPlayerNationality;
    private EditText editTextPlayerClub;
    private EditText editTextPlayerContractCommence;
    private EditText editTextPlayerContractExp;
    private RadioGroup radioGroupPosition;
    private Button buttonRegister;

    private int currentYear;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        currentYear = Calendar.getInstance().get(Calendar.YEAR);
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

        editTextPlayerContractCommence.setText(getString(R.string.commence_year, currentYear));

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FifaDatabase.destroyInstance();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            insertPlayerProfile();
        }
    }

    @Override
    public void checkStatusCallback(boolean isSuccess) {
        if (isSuccess) {
            clearPlayerProfileForm();
            Toast.makeText(getContext()
                    , R.string.status_register_successful
                    , Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void insertPlayerProfile() {
        String playerName = editTextPlayerName.getText().toString().trim();
        String playerNationality = editTextPlayerNationality.getText().toString().trim();
        String playerClub = editTextPlayerClub.getText().toString().trim();
        String playerContractExp = editTextPlayerContractExp.getText().toString().trim();
        int checkedRadioButtonId = radioGroupPosition.getCheckedRadioButtonId();
        if (isPlayerProfileInvalid(playerName,
                playerNationality,
                playerClub,
                playerContractExp,
                checkedRadioButtonId)) {
            showPlayerProfileInvalid();
        } else {
            insertPlayerProfileToDatabase(playerName,
                    playerNationality,
                    playerClub,
                    playerContractExp,
                    getPositionByRadioButton(checkedRadioButtonId),
                    currentYear);
        }
    }

    private boolean isPlayerProfileInvalid(String playerName,
                                           String playerNationality,
                                           String playerClub,
                                           String playerContractExp,
                                           int checkedRadioButtonId) {
        return playerName.isEmpty()
                || playerNationality.isEmpty()
                || playerClub.isEmpty()
                || playerContractExp.isEmpty()
                || checkedRadioButtonId == -1;
    }

    private void showPlayerProfileInvalid() {
        Toast.makeText(getContext()
                , R.string.error_form_invalid
                , Toast.LENGTH_SHORT)
                .show();
    }

    private void insertPlayerProfileToDatabase(String playerName,
                                               String playerNationality,
                                               String playerClub,
                                               String playerContractExp,
                                               String playerPosition,
                                               int currentYear) {
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

    private void clearPlayerProfileForm() {
        editTextPlayerName.setText("");
        radioGroupPosition.clearCheck();
        editTextPlayerNationality.setText("");
        editTextPlayerClub.setText("");
        editTextPlayerContractExp.setText("");
    }

    private String getPositionByRadioButton(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
            case R.id.rb_fw:
                return POSITION_FORWARD;
            case R.id.rb_mf:
                return POSITION_MID_FIELDER;
            case R.id.rb_df:
                return POSITION_DEFENDER;
            case R.id.rb_gk:
                return POSITION_GOAL_KEEPER;
        }
        return null;
    }
}
