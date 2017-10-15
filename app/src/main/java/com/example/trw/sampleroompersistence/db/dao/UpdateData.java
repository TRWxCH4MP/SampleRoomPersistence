package com.example.trw.sampleroompersistence.db.dao;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trw.sampleroompersistence.db.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;

/**
 * Created by TRW on 8/10/2560.
 */

public class UpdateData {

    public static void onUpdateContract(
            final Context context
            , final int playerId
            , final int playerContractCommence
            , final int playerContractExp
            , final SendStatusCallback callback) {

        new updateContract(context
                , playerId
                , playerContractCommence
                , playerContractExp
                , callback).execute();
    }

    public static class updateContract extends AsyncTask<Void, Void, Void> {
        Context context;
        int playerId;
        int playerContractCommence;
        int playerContractExp;
        SendStatusCallback callback;

        public updateContract(Context context
                , int playerId
                , int playerContractCommence
                , int playerContractExp
                , SendStatusCallback callback) {
            this.context = context;
            this.playerId = playerId;
            this.playerContractCommence = playerContractCommence;
            this.playerContractExp = playerContractExp;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FifaDatabase.getAppDatabase(context).fifaDao().updatePlayerContract(
                    playerId
                    , playerContractCommence
                    , playerContractExp);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.checkStatusCallback(true);
        }

    }
}
