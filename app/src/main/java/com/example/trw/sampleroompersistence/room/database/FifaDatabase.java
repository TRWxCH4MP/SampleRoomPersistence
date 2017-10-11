package com.example.trw.sampleroompersistence.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.trw.sampleroompersistence.room.dao.FifaDao;
import com.example.trw.sampleroompersistence.room.entity.AwardEntity;
import com.example.trw.sampleroompersistence.room.entity.ProfileEntity;


/**
 * Created by TRW on 2/10/2560.
 */

@Database(entities = {AwardEntity.class, ProfileEntity.class}, version = 1)
public abstract class FifaDatabase extends RoomDatabase {

    private static FifaDatabase INSTANCE;
    static String TAG = "FifaDatabase";

    public abstract FifaDao fifaDao();

    public static FifaDatabase getAppDatabase(Context context) {
        String DATABASE_NAME = "db-fifa";
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context
                            , FifaDatabase.class
                            , DATABASE_NAME)
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
