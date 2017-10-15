package com.example.trw.sampleroompersistence.db.dao;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trw.sampleroompersistence.db.callback.SendDataCallback;
import com.example.trw.sampleroompersistence.db.callback.SendListDataCallback;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;
import com.example.trw.sampleroompersistence.db.entity.PlayerContract;
import com.example.trw.sampleroompersistence.db.entity.PlayerWithAward;
import com.example.trw.sampleroompersistence.db.entity.ProfileEntity;

import java.util.List;

/**
 * Created by TRW on 2/10/2560.
 */

public class QueryData {

    static String TAG = "QueryData";

    public static void onLoadProfile(final Context context, final SendListDataCallback callback) {

        new loadPlayerProfile(context, callback).execute();
    }

    public static class loadPlayerProfile extends AsyncTask<Void, Void, Void> {
        Context context;
        SendListDataCallback callback;
        List<ProfileEntity> listPlayer;

        public loadPlayerProfile(Context context, SendListDataCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listPlayer = FifaDatabase.getAppDatabase(context).fifaDao().loadAllProfile();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!listPlayer.isEmpty()) {
                callback.loadProfileDataCallback(listPlayer, true);
            } else {
                callback.loadProfileDataCallback(listPlayer, false);
            }
//            Log.d(TAG, "onPostExecute Successfully: ");
        }

        @Override
        protected void onPreExecute() {
//            Log.d(TAG, "onPreExecute loading...: ");
        }

    }

    public static void onFindPlayerIdWithPlayerName(final Context context, String playerName, SendDataCallback callback) {

        new findPlayerIdWithPlayerName(context, playerName, callback).execute();
    }

    public static class findPlayerIdWithPlayerName extends AsyncTask<Void, Void, Void> {
        Context context;
        SendDataCallback callback;
        int playerId;
        String playerName;

        public findPlayerIdWithPlayerName(Context context, String playerName, SendDataCallback callback) {
            this.context = context;
            this.callback = callback;
            this.playerName = playerName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            playerId = FifaDatabase.getAppDatabase(context).fifaDao().findPlayerIdWithPlayerName(playerName);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (playerId > 0) {
                callback.findPlayerIdCallback(playerId, true);
            } else {
                callback.findPlayerIdCallback(playerId, false);
            }
//            Log.d(TAG, "onPostExecute Successfully: ");
        }

        @Override
        protected void onPreExecute() {
//            Log.d(TAG, "onPreExecute loading...: ");
        }

    }

    public static void onLoadPlayerWithAward(final Context context, final SendListDataCallback callback) {

        new loadPlayerWithAward(context, callback).execute();
    }

    public static class loadPlayerWithAward extends AsyncTask<Void, Void, Void> {
        Context context;
        SendListDataCallback callback;
        List<PlayerWithAward> listPlayerWithAward;


        public loadPlayerWithAward(Context context, SendListDataCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listPlayerWithAward = FifaDatabase.getAppDatabase(context).fifaDao().loadPlayerWithAward();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!listPlayerWithAward.isEmpty()) {
                callback.loadPlayerWithAwardDataCallback(listPlayerWithAward, true);
            } else {
                callback.loadPlayerWithAwardDataCallback(listPlayerWithAward, false);
            }
//            Log.d(TAG, "onPostExecute Successfully: ");
        }

        @Override
        protected void onPreExecute() {
//            Log.d(TAG, "onPreExecute loading...: ");
        }
    }

    public static void onLoadPlayerWithContract(final Context context, final SendListDataCallback callback) {

        new LoadPlayerWithContract(context, callback).execute();
    }

    private static class LoadPlayerWithContract extends AsyncTask<Void, Void, Void> {
        Context context;
        SendListDataCallback callback;
        List<PlayerContract> listPlayerContract;

        public LoadPlayerWithContract(Context context, SendListDataCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            listPlayerContract = FifaDatabase.getAppDatabase(context).fifaDao().loadAllContract();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!listPlayerContract.isEmpty()) {
                callback.loadPlayerContractCallback(listPlayerContract, true);
            } else {
                callback.loadPlayerContractCallback(listPlayerContract, false);
            }
//            Log.d(TAG, "onPostExecute Successfully: ");
        }
    }
}
