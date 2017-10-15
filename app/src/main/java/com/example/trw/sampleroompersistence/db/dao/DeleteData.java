package com.example.trw.sampleroompersistence.db.dao;

import android.content.Context;
import android.os.AsyncTask;

import com.example.trw.sampleroompersistence.db.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.db.database.FifaDatabase;

/**
 * Created by TRW on 9/10/2560.
 */

public class DeleteData {

    public static void onDeletePlayerByPlayerId(
            final Context context
            , final int playerId
            , final SendStatusCallback callback) {

        new deletePlayerByPlayerId(context
                , playerId
                , callback).execute();
    }

    public static class deletePlayerByPlayerId extends AsyncTask<Void, Void, Void> {
        Context context;
        int playerId;
        SendStatusCallback callback;

        public deletePlayerByPlayerId(Context context, int playerId, SendStatusCallback callback) {
            this.context = context;
            this.playerId = playerId;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FifaDatabase.getAppDatabase(context).fifaDao().deletePlayerByPlayerId(playerId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.checkStatusCallback(true);
        }
    }

    public static void onDeleteAllPlayer(
            final Context context
            , final SendStatusCallback callback) {

        new deleteAllPlayer(context, callback).execute();
    }

    public static class deleteAllPlayer extends AsyncTask<Void, Void, Void> {
        Context context;
        SendStatusCallback callback;

        public deleteAllPlayer(Context context, SendStatusCallback callback) {
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FifaDatabase.getAppDatabase(context).fifaDao().deleteAllPlayer();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.checkStatusCallback(true);
        }
    }

}
