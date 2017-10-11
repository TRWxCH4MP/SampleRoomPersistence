package com.example.trw.sampleroompersistence.room.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.trw.sampleroompersistence.callback.SendStatusCallback;
import com.example.trw.sampleroompersistence.room.database.FifaDatabase;
import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;

/**
 * Created by TRW on 2/10/2560.
 */

public class InsertData {

    public static String TAG = "InsertData";

    public static void onInsertProfile(final Context context
            , final ProfileEntity profileEntity
            , final SendStatusCallback callback) {

        new insertPlayerProfile(context, profileEntity, callback).execute();
    }

    public static class insertPlayerProfile extends AsyncTask<Void, Void, Void> {
        Context context;
        ProfileEntity profileEntity;
        SendStatusCallback callback;

        public insertPlayerProfile(Context context, ProfileEntity profileEntity, SendStatusCallback callback) {
            this.context = context;
            this.profileEntity = profileEntity;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FifaDatabase.getAppDatabase(context).fifaDao().insertProfile(profileEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.checkStatusCallback(true);
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute loading...: ");
        }

    }

    public static void onInsertAward(final Context context
            , final AwardEntity awardEntity
            , final SendStatusCallback callback) {

        new insertAward(context, awardEntity, callback).execute();
    }

    public static class insertAward extends AsyncTask<Void, Void, Void> {
        Context context;
        AwardEntity awardEntity;
        SendStatusCallback callback;

        public insertAward(Context context, AwardEntity awardEntity, SendStatusCallback callback) {
            this.context = context;
            this.awardEntity = awardEntity;
            this.callback = callback;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            FifaDatabase.getAppDatabase(context).fifaDao().insertAward(awardEntity);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.checkStatusCallback(true);
        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute loading...: ");
        }

    }
}
